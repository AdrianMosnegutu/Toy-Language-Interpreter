package controller;

import java.util.Arrays;
import java.util.List;

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
    private List<IStatement> programStatements;

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
                "Create a parallel thread");

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

        controller.setProgramDescription(programDescriptions.get(selectedIndex));
        controller.setProgram(programStatements.get(selectedIndex));
    }
}
