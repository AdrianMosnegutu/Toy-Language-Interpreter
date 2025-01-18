package controller;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
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

public class DebugMenuController {
    private IController controller;

    private void update(ProgramState program) {
        IExecutionStack executionStack = program.getExecutionStack();
        ISymbolsTable symbolsTable = program.getSymbolsTable();
        IHeap heap = program.getHeap();
        IFileTable fileTable = program.getFileTable();
        IOutputList output = program.getOutput();

        executionStackList.setItems(FXCollections.observableArrayList(
                executionStack.getAll().reversed().stream()
                        .map(statement -> statement.toString())
                        .toList()));

        ObservableList<Map.Entry<String, String>> variableEntries = FXCollections.observableArrayList(
                symbolsTable.getAll().entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList()));
        variableNameColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        variableValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        variablesTable.setItems(variableEntries);

        ObservableList<Map.Entry<Integer, String>> heapEntries = FXCollections.observableArrayList(
                heap.getAll().entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
                        .collect(Collectors.toList()));
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        heapTable.setItems(heapEntries);

        filesList.setItems(FXCollections.observableArrayList(fileTable.getAll().stream()
                .map(value -> value.toString()).toList()));

        outputList.setItems(FXCollections.observableArrayList(output.getAll().stream()
                .map(value -> value.toString()).toList()));
    }

    public void setProgramDescription(String programDescription) {
        programDescriptionText.setText(programDescription);
    }

    public void setProgram(IStatement initialProgram) {
        ProgramState programState = new ProgramState(initialProgram);
        controller = new Controller(programState);
        List<ProgramState> programStates = controller.getProgramStates();

        numberOfThreadsText.setText(String.format("%s", programStates.size()));

        pidsList.setItems(
                FXCollections.observableArrayList(programStates.stream().map(program -> program.getPid()).toList()));
        pidsList.getSelectionModel().select(0);

        update(programStates.getFirst());

        pidsList.getSelectionModel().selectedItemProperty().addListener((obsrvable, oldValue, newValue) -> {
            List<ProgramState> programs = controller.getProgramStates();
            int selectedIndex = pidsList.getSelectionModel().getSelectedIndex();
            update(programs.get(selectedIndex));
        });
    }

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

    public void executeOneStep(ActionEvent e) {
        List<ProgramState> programStates = controller.getProgramStates();

        if (programStates.isEmpty()) {
            return;
        }

        controller.oneStep();
        update(controller.getProgramStates().getFirst());

        programStates = controller.getProgramStates();
        numberOfThreadsText.setText(String.format("%s", programStates.size()));
        pidsList.setItems(
                FXCollections.observableArrayList(programStates.stream().map(program -> program.getPid()).toList()));
    }
}
