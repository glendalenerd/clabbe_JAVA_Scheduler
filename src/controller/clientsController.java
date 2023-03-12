package src.controller;

import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.model.clientModel;
import src.utilities.utilityFunctions;

import java.io.IOException;

public class clientsController {
    private final ObservableList<clientModel> allClients = FXCollections.observableArrayList();
    @FXML
    public TableView<clientModel> clientTable;
    @FXML
    public TableColumn<clientModel, Integer> clientIdColumn;
    @FXML
    public TableColumn<clientModel, String> clientNameColumn;
    @FXML
    public TableColumn<clientModel, String> clientEmailColumn;
    @FXML
    public TableColumn<clientModel, String> hairColorColumn;
    @FXML
    public TableColumn<clientModel, Integer> activeStatusColumn;
    @FXML
    public TableColumn<clientModel, String> stateProvColumn;
    @FXML
    public TableColumn<clientModel, String> clientCountryColumn;
    @FXML
    public TableColumn<clientModel, String> clientPostalCodeColumn;
    @FXML
    public TextField clientNameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField prefHairColorField;
    @FXML
    public CheckBox activeStatusBox;
    @FXML
    public ChoiceBox countryChoiceBox;
    @FXML
    public TextField stateProvField;
    @FXML
    public TextField postalCodeField;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    @FXML
    public Button backButton;
    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }
}
