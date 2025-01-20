package controller;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import model.helpers.Pair;
import model.statements.IStatement;
import model.states.ProgramState;
import model.values.IValue;
import model.values.StringValue;

public class DebugMenuController {
    @FXML
    private Text programDescriptionText;

    @FXML
    private Text selectedThreadPidText;

    @FXML
    private ListView<Integer> pidsList;

    @FXML
    private ListView<String> executionStackList;

    @FXML
    private TableView<Pair<String, String>> variablesTable;

    @FXML
    private TableColumn<Pair<String, String>, String> variableNameColumn;

    @FXML
    private TableColumn<Pair<String, String>, String> variableValueColumn;

    @FXML
    private TableView<Pair<Integer, String>> heapTable;

    @FXML
    private TableColumn<Pair<Integer, String>, String> heapAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, String>, String> heapValueColumn;

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

    public void setProgram(IStatement initialProgram, String logFilePath) {
        ProgramState initialState = new ProgramState(initialProgram);
        controller = new Controller(initialState, logFilePath);

        updateExecutionStackList(initialState.getExecutionStack());
        pidsList.setItems(FXCollections.observableArrayList(
                controller.getProgramStates().stream().map(ProgramState::getPid).toList()));

        pidsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<ProgramState> programStates = controller.getProgramStates();
            ProgramState newState;
            Integer selectedPid;

            if (newValue == null) {
                if (programStates.isEmpty()) {
                    return;
                }

                newState = programStates.getFirst();
                selectedPid = newState.getPid();
            } else {
                newState = programStates.stream()
                        .filter(state -> state.getPid() == newValue)
                        .findFirst()
                        .orElse(null);
                selectedPid = newValue;
            }

            if (oldValue != null) {
                removeListenersFromState(
                        programStates.stream()
                                .filter(state -> state.getPid() == oldValue)
                                .findFirst()
                                .orElse(null));
            }

            selectedThreadPidText.setText(selectedPid.toString());
            pidsList.getSelectionModel().select(selectedPid);

            addListenersToState(newState);
            updateUIForState(newState);
        });

        pidsList.getSelectionModel().select(0);
    }

    public void executeOneStep(ActionEvent event) {
        if (controller.executionHasCompleted()) {
            return;
        }

        controller.executeOneStep();

        pidsList.setItems(FXCollections.observableArrayList(
                controller.getProgramStates().stream()
                        .map(ProgramState::getPid)
                        .toList()));

        if (controller.executionHasCompleted()) {
            selectedThreadPidText.setText("-");
        }
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

        state.getExecutionStack().getAll().removeListener(executionStackListener);
        state.getSymbolsTable().getAll().removeListener(symbolsTableListener);
        state.getOutput().getAll().removeListener(outputListListener);
        state.getFileTable().getAll().removeListener(fileTableListener);
        state.getHeap().getAll().removeListener(heapListener);
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
        updateTableView(variablesTable, tableEntries(symbolsTable.getAll().entrySet()), variableNameColumn,
                variableValueColumn);
    }

    private void updateHeapTable(IHeap heap) {
        updateTableView(heapTable, tableEntries(heap.getAll().entrySet()), heapAddressColumn, heapValueColumn);
    }

    private void updateFileList(IFileTable fileTable) {
        updateListView(filesList, fileTable.getAll().keySet().stream().map(StringValue::toString).toList());
    }

    private void updateOutputList(IOutputList output) {
        updateListView(outputList, output.getAll().stream().map(IValue::toString).toList());
    }

    private <K, V> ObservableList<Pair<K, String>> tableEntries(Set<Map.Entry<K, V>> entries) {
        return FXCollections.observableArrayList(entries.stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.toList()));
    }

    private <T> void updateListView(ListView<T> listView, List<T> items) {
        Platform.runLater(() -> listView.setItems(FXCollections.observableArrayList(items)));
    }

    private <K, V> void updateTableView(
            TableView<Pair<K, V>> tableView,
            ObservableList<Pair<K, V>> items,
            TableColumn<Pair<K, V>, String> column1,
            TableColumn<Pair<K, V>, String> column2) {
        Platform.runLater(() -> {
            tableView.setItems(items);

            column1.setCellValueFactory(new PropertyValueFactory<>("first"));
            column1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));

            column2.setCellValueFactory(new PropertyValueFactory<>("second"));
            column2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        });
    }
}
