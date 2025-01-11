package controller;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import model.statements.IStatement;
import model.states.ProgramExamples;

public class InterpreterController {
    private ObservableList<String> programDescriptions;
    private List<IStatement> programStatements;

    @FXML
    private ListView<String> examplesList;

    @FXML
    private TextArea codePreview;

    @FXML
    public void initialize() {
        programDescriptions = FXCollections.observableArrayList(
                "Print a value",
                "Perform arithmetic operations",
                "Use the 'if' statement",
                "Read data from a file",
                "Allocate data to the heap",
                "Read data from the heap",
                "Write data to the heap",
                "Use the garbage collector",
                "Use the 'while' statement",
                "Fork a new program thread");

        programStatements = Arrays.asList(
                ProgramExamples.printValueExample(),
                ProgramExamples.arithmeticOperationsExample(),
                ProgramExamples.ifStatementExample(),
                ProgramExamples.readFromFileExample(),
                ProgramExamples.allocateToHeapExample(),
                ProgramExamples.readFromHeapExample(),
                ProgramExamples.writeToHeapExample(),
                ProgramExamples.garbageCollectorExample(),
                ProgramExamples.whileStatementExample(),
                ProgramExamples.forkStatementExample());

        examplesList.setItems(programDescriptions);
        examplesList.getSelectionModel().selectedItemProperty().addListener((obsrvable, oldValue, newValue) -> {
            int selectedIndex = examplesList.getSelectionModel().getSelectedIndex();
            codePreview.setText(programStatements.get(selectedIndex).toString());
        });
        examplesList.getSelectionModel().select(0);
    }

    @FXML
    public void selectExample(ActionEvent e) {
        System.out.println(examplesList.getSelectionModel().getSelectedItem());
    }
}
