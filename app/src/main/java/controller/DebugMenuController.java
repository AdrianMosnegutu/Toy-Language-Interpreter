package controller;

import java.io.BufferedReader;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.collections.WeakMapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.adt.IExecutionStack;
import model.adt.IFileTable;
import model.adt.IHeap;
import model.adt.IOutputList;
import model.adt.ISymbolsTable;
import model.statements.IStatement;
import model.states.ProgramState;
import model.values.IValue;
import model.values.StringValue;

public class DebugMenuController {
    @FXML
    private Text programDescriptionText;

    @FXML
    private Text numberOfThreadsText;

    @FXML
    private ListView<Integer> pidsList;

    @FXML
    private ListView<String> executionStackList;

    @FXML
    private TableView<Map.Entry<String, String>> variablesTable;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> variableNameColumn;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> variableValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, String>> heapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> heapValueColumn;

    @FXML
    private ListView<String> filesList;

    @FXML
    private ListView<String> outputList;

    private IController controller;

    private WeakListChangeListener<IStatement> executionStackListener;
    private WeakMapChangeListener<String, IValue> symbolsTableListener;
    private WeakListChangeListener<IValue> outputListListener;
    private WeakMapChangeListener<StringValue, BufferedReader> fileTableListener;
    private WeakMapChangeListener<Integer, IValue> heapListener;

    public void updateProgramDescription(String programDescription) {
        programDescriptionText.setText(programDescription);
    }

    public void setProgram(IStatement initialProgram) {
        controller = new Controller(new ProgramState(initialProgram));
        List<ProgramState> programStates = controller.getProgramStates();

        updateExecutionStackList(programStates.getFirst().getExecutionStack());
        numberOfThreadsText.setText(String.valueOf(programStates.size()));
        pidsList.setItems(FXCollections.observableArrayList(
                programStates.stream().map(ProgramState::getPid).toList()));

        pidsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }

            ProgramState newState = controller.getProgramStates().stream()
                    .filter(state -> state.getPid() == newValue)
                    .findFirst()
                    .orElse(null);

            if (newState == null) {
                return;
            }

            if (oldValue != null) {
                removeListenersFromState(
                        controller.getProgramStates().stream()
                                .filter(state -> state.getPid() == oldValue)
                                .findFirst()
                                .orElse(null));
            }

            addListenersToState(newState);
            updateUIForState(newState);
        });

        pidsList.getSelectionModel().select(0);
    }

    public void executeOneStep(ActionEvent e) {
        try {
            controller.executeOneStep();
        } catch (Exception err) {
            return;
        }

        List<ProgramState> programStates = controller.getProgramStates();
        numberOfThreadsText.setText(String.valueOf(programStates.size()));
        pidsList.setItems(FXCollections.observableArrayList(programStates.stream().map(ProgramState::getPid).toList()));
    }

    private void addListenersToState(ProgramState state) {
        if (state == null) {
            return;
        }

        IExecutionStack executionStack = state.getExecutionStack();
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IOutputList output = state.getOutput();

        executionStackListener = new WeakListChangeListener<>(change -> updateExecutionStackList(executionStack));
        symbolsTableListener = new WeakMapChangeListener<>(change -> updateSymbolsTable(symbolsTable));
        outputListListener = new WeakListChangeListener<>(change -> updateOutputList(output));
        fileTableListener = new WeakMapChangeListener<>(change -> updateFileList(fileTable));
        heapListener = new WeakMapChangeListener<>(change -> updateHeapTable(heap));

        executionStack.getAll().addListener(executionStackListener);
        symbolsTable.getAll().addListener(symbolsTableListener);
        output.getAll().addListener(outputListListener);
        fileTable.getAll().addListener(fileTableListener);
        heap.getAll().addListener(heapListener);
    }

    private void removeListenersFromState(ProgramState state) {
        if (state == null) {
            return;
        }

        IExecutionStack executionStack = state.getExecutionStack();
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IOutputList output = state.getOutput();

        executionStack.getAll().removeListener(executionStackListener);
        symbolsTable.getAll().removeListener(symbolsTableListener);
        output.getAll().removeListener(outputListListener);
        fileTable.getAll().removeListener(fileTableListener);
        heap.getAll().removeListener(heapListener);
    }

    private void updateUIForState(ProgramState state) {
        updateExecutionStackList(state.getExecutionStack());
        updateOutputList(state.getOutput());
        updateHeapTable(state.getHeap());
        updateSymbolsTable(state.getSymbolsTable());
        updateFileList(state.getFileTable());
    }

    private void updateExecutionStackList(IExecutionStack executionStack) {
        updateListView(executionStackList,
                executionStack.getAll().reversed().stream().map(IStatement::toString).toList());
    }

    private void updateSymbolsTable(ISymbolsTable symbolsTable) {
        ObservableList<Map.Entry<String, String>> variableEntries = FXCollections.observableArrayList(
                symbolsTable.getAll().entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList()));

        updateTableView(variablesTable, variableEntries, variableNameColumn, variableValueColumn);
    }

    private void updateHeapTable(IHeap heap) {
        ObservableList<Map.Entry<Integer, String>> heapEntries = FXCollections.observableArrayList(
                heap.getAll().entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList()));

        updateTableView(heapTable, heapEntries, heapAddressColumn, heapValueColumn);
    }

    private void updateFileList(IFileTable fileTable) {
        updateListView(filesList, fileTable.getAll().keySet().stream().map(StringValue::toString).toList());
    }

    private void updateOutputList(IOutputList output) {
        updateListView(outputList, output.getAll().stream().map(IValue::toString).toList());
    }

    private <T> void updateListView(ListView<T> listView, List<T> items) {
        Platform.runLater(() -> listView.setItems(FXCollections.observableArrayList(items)));
    }

    private <K, V> void updateTableView(
            TableView<Map.Entry<K, V>> tableView,
            ObservableList<Map.Entry<K, V>> items,
            TableColumn<Map.Entry<K, V>, String> column1,
            TableColumn<Map.Entry<K, V>, String> column2) {
        Platform.runLater(() -> {
            tableView.setItems(items);

            column1.setCellValueFactory(new PropertyValueFactory<>("key"));
            column1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.49666));

            column2.setCellValueFactory(new PropertyValueFactory<>("value"));
            column2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.49666));
        });
    }
}
