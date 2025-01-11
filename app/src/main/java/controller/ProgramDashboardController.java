package controller;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import model.values.IValue;

public class ProgramDashboardController {
    private String programDescription;

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
        programDescriptionText.setText(programDescription);
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
    private TableView<Map.Entry<String, IValue>> variablesTable;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> variableNameColumn;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> variableValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> heapValueColumn;

    @FXML
    private ListView<String> filesList;

    @FXML
    private ListView<String> outputList;

    @FXML
    public void initialize() {

    }
}
