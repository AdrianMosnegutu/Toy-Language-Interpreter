package controller;

import java.io.BufferedReader;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
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
    private IController controller;

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

    public void setProgramDescription(String programDescription) {
        programDescriptionText.setText(programDescription);
    }

    private void updateExecutionStackList(IExecutionStack executionStack) {
        Platform.runLater(() -> {
            executionStackList.setItems(FXCollections.observableArrayList(
                    executionStack.getAll().reversed().stream()
                            .map(IStatement::toString)
                            .toList()));
        });
    }

    private void updateSymbolsTable(ISymbolsTable symbolsTable) {
        Platform.runLater(() -> {
            ObservableList<Map.Entry<String, String>> variableEntries = FXCollections.observableArrayList(
                    symbolsTable.getAll().entrySet().stream()
                            .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
                            .collect(Collectors.toList()));

            variableNameColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
            variableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
            variablesTable.setItems(variableEntries);
        });
    }

    private void updateHeapTable(IHeap heap) {
        Platform.runLater(() -> {
            ObservableList<Map.Entry<Integer, String>> heapEntries = FXCollections.observableArrayList(
                    heap.getAll().entrySet().stream()
                            .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
                            .collect(Collectors.toList()));

            heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
            heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
            heapTable.setItems(heapEntries);
        });
    }

    private void updateFileList(IFileTable fileTable) {
        Platform.runLater(() -> {
            filesList.setItems(FXCollections.observableArrayList(fileTable.getAll().keySet().stream()
                    .map(value -> value.toString()).toList()));
        });
    }

    private void updateOutputList(IOutputList output) {
        Platform.runLater(() -> {
            outputList.setItems(FXCollections.observableArrayList(output.getAll().stream()
                    .map(value -> value.toString()).toList()));
        });
    }

    private void addListenersToState(ProgramState state) {
        IExecutionStack executionStack = state.getExecutionStack();
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IOutputList output = state.getOutput();

        executionStack.getAll().addListener(
                (ListChangeListener<IStatement>) stackChange -> updateExecutionStackList(executionStack));
        fileTable.getAll().addListener(
                (MapChangeListener<StringValue, BufferedReader>) c -> updateFileList(fileTable));
        symbolsTable.getAll().addListener(
                (MapChangeListener<String, IValue>) c -> updateSymbolsTable(symbolsTable));
        heap.getAll().addListener((MapChangeListener<Integer, IValue>) c -> updateHeapTable(heap));
        output.getAll().addListener((ListChangeListener<IValue>) c -> updateOutputList(output));
    }

    private void removeListenersFromState(ProgramState state) {
        IExecutionStack executionStack = state.getExecutionStack();
        ISymbolsTable symbolsTable = state.getSymbolsTable();
        IFileTable fileTable = state.getFileTable();
        IHeap heap = state.getHeap();
        IOutputList output = state.getOutput();

        executionStack.getAll().removeListener(
                (ListChangeListener<IStatement>) stackChange -> updateExecutionStackList(executionStack));
        fileTable.getAll().removeListener(
                (MapChangeListener<StringValue, BufferedReader>) c -> updateFileList(fileTable));
        symbolsTable.getAll().removeListener(
                (MapChangeListener<String, IValue>) c -> updateSymbolsTable(symbolsTable));
        heap.getAll().removeListener((MapChangeListener<Integer, IValue>) c -> updateHeapTable(heap));
        output.getAll().removeListener((ListChangeListener<IValue>) c -> updateOutputList(output));
    }

    public void setProgram(IStatement initialProgram) {
        controller = new Controller(new ProgramState(initialProgram));
        ObservableList<ProgramState> programStates = controller.getProgramStates();

        updateExecutionStackList(programStates.getFirst().getExecutionStack());
        numberOfThreadsText.setText(String.format("%s", programStates.size()));
        pidsList.setItems(FXCollections.observableArrayList(
                programStates.stream().map(program -> program.getPid()).toList()));

        pidsList.getSelectionModel().selectedItemProperty().addListener((obsrvable, oldValue, newValue) -> {
            ObservableList<ProgramState> programStates2 = controller.getProgramStates();
            int oldSelectedIndex = 0;

            int selectedIndex = pidsList.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                selectedIndex = pidsList.getFocusModel().getFocusedIndex();
            }

            if (oldValue != null) {
                try {
                    oldSelectedIndex = programStates2.indexOf(
                            programStates2.stream().findFirst().filter(state -> state.getPid() == oldValue).get());
                } catch (Exception err) {
                    oldSelectedIndex = selectedIndex;
                }
            }

            ProgramState newState;
            try {
                newState = controller.getProgramStates().get(selectedIndex);
            } catch (Exception err) {
                return;
            }

            removeListenersFromState(controller.getProgramStates().get(oldSelectedIndex));
            addListenersToState(controller.getProgramStates().get(selectedIndex));

            updateExecutionStackList(newState.getExecutionStack());
            updateOutputList(newState.getOutput());
            updateHeapTable(newState.getHeap());
            updateSymbolsTable(newState.getSymbolsTable());
            updateFileList(newState.getFileTable());
        });

        pidsList.getSelectionModel().select(0);
    }

    public void executeOneStep(ActionEvent e) {
        try {
            controller.oneStep();
        } catch (Exception err) {
            return;
        }

        ObservableList<ProgramState> programStates = controller.getProgramStates();
        numberOfThreadsText.setText(String.format("%s", programStates.size()));

        pidsList.setItems(FXCollections.observableArrayList(
                programStates.stream().map(program -> program.getPid()).toList()));
    }
}
