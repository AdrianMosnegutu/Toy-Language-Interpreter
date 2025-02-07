package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import model.statements.IStatement;
import model.states.ProgramExamples;

public class MainMenuController {
    private ObservableList<String> programDescriptions;
    private List<String> programLogFiles;
    private List<IStatement> programStatements;

    final String logsDirectoryPath = "build/logs/";

    @FXML
    private TabPane applicationTabs;

    @FXML
    private ListView<String> examplesList;

    @FXML
    private TextArea codePreview;

    @FXML
    public void initialize() {
        programDescriptions = FXCollections.observableArrayList(
                "Print a value to the console",
                "Perform arithmetic operations",
                "Use the 'if' statement",
                "Read data from a file",
                "Allocate data to the heap",
                "Read data from the heap",
                "Write data to the heap",
                "Use the garbage collector",
                "Use the 'while' statement",
                "Use the 'repeat until' statement",
                "Create a parallel process",
                "Use a barrier synchronization mechanism");

        programLogFiles = new ArrayList<>(Arrays.asList(
                "printValue.log",
                "arithmeticOperations.log",
                "ifStatement.log",
                "readFile.log",
                "allocateHeap.log",
                "readHeap.log",
                "writeHeap.log",
                "garbageCollector.log",
                "whileStatement.log",
                "repeatUntil.log",
                "forkStatement.log",
                "barrier.log"));

        programStatements = new ArrayList<>(Arrays.asList(
                ProgramExamples.printValueExample(),
                ProgramExamples.arithmeticOperationsExample(),
                ProgramExamples.ifStatementExample(),
                ProgramExamples.readFromFileExample(),
                ProgramExamples.allocateToHeapExample(),
                ProgramExamples.readFromHeapExample(),
                ProgramExamples.writeToHeapExample(),
                ProgramExamples.garbageCollectorExample(),
                ProgramExamples.whileStatementExample(),
                ProgramExamples.forkStatementExample(),
                ProgramExamples.repeatUntilExample(),
                ProgramExamples.cyclicBarrierExample()));

        typecheckExamples();

        examplesList.setItems(programDescriptions);

        examplesList.getSelectionModel().selectedItemProperty().addListener((obsrvable, oldValue, newValue) -> {
            int selectedIndex = examplesList.getSelectionModel().getSelectedIndex();
            codePreview.setText(programStatements.get(selectedIndex).toString());
        });
        examplesList.getSelectionModel().select(0);

        examplesList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                selectExample(null);
            }
        });
    }

    @FXML
    public void selectExample(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../DebugMenu.fxml"));

        Node programDashboard;
        DebugMenuController controller;

        try {
            programDashboard = loader.load();
            controller = loader.getController();
        } catch (Exception err) {
            err.printStackTrace();
            return;
        }

        int selectedIndex = examplesList.getSelectionModel().getSelectedIndex();
        int numOfTabs = applicationTabs.getTabs().size();

        Tab programDashboardTab = new Tab(programDescriptions.get(selectedIndex), programDashboard);
        programDashboardTab.setClosable(true);

        applicationTabs.getTabs().add(programDashboardTab);
        applicationTabs.getSelectionModel().select(numOfTabs);

        controller.updateProgramDescription(programDescriptions.get(selectedIndex));
        controller.setProgram(programStatements.get(selectedIndex),
                logsDirectoryPath + programLogFiles.get(selectedIndex));
    }

    private void typecheckExamples() {
        for (int index = 0; index < programStatements.size(); ++index) {
            try {
                programStatements.get(index).typecheck(new HashMap<>());
                System.out.println();
                System.out.println("Type checking passed for example: " + programDescriptions.get(index));
            } catch (MyException e) {
                System.out.println();
                System.out.println(String.format("Type error for example '%s'", programDescriptions.get(index)));
                System.out.println(e);

                programDescriptions.remove(index);
                programLogFiles.remove(index);
                programStatements.remove(index);

                index--;
            }
        }
    }
}
