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

public class InterpreterController {
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

        // Populate the examples list.
        examplesList.setItems(programDescriptions);

        // Make it so that when an example is selected, the right pane displays a preview of the program.
        examplesList.getSelectionModel().selectedItemProperty().addListener((obsrvable, oldValue, newValue) -> {
            int selectedIndex = examplesList.getSelectionModel().getSelectedIndex();
            codePreview.setText(programStatements.get(selectedIndex).toString());
        });

        // By default, select the first example at application start
        examplesList.getSelectionModel().select(0);

        // Make it so that when the 'ENTER' key is pressed, the application starts the selected example program.
        examplesList.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                selectExample(null);
            }
        });
    }

    @FXML
    public void selectExample(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../ProgramDashboard.fxml"));

        Node programDashboard;
        ProgramDashboardController controller;

        try {
            // Load the dashboard fxml file and get its controller
            programDashboard = loader.load();
            controller = loader.getController();
        } catch (Exception err) {
            err.printStackTrace();
            return;
        }

        int selectedIndex = examplesList.getSelectionModel().getSelectedIndex();
        int numOfTabs = applicationTabs.getTabs().size();

        // Create a new tab for displaying the current example program information
        Tab programDashboardTab = new Tab(programDescriptions.get(selectedIndex), programDashboard);
        programDashboardTab.setClosable(true);

        // Add the new tab and select it 
        applicationTabs.getTabs().add(programDashboardTab);
        applicationTabs.getSelectionModel().select(numOfTabs);

        // In the example program's controller, assign the program description and its statement.
        controller.setProgramDescription(programDescriptions.get(selectedIndex));
        controller.setProgram(programStatements.get(selectedIndex));
    }
}
