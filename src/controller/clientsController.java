package src.controller;

import com.mysql.cj.xdevapi.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.database.clientQueries;
import src.model.clientModel;
import src.utilities.utilityFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class clientsController implements Initializable {
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
    @FXML
    public Button clearFieldsButton;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allClients.setAll(clientQueries.getClientList());
        } catch (SQLException i) {
            throw new RuntimeException(i);
        }
    clientTable.setItems(allClients);
    clientTable.refresh();
    clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));
    clientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("clientEmail"));
    hairColorColumn.setCellValueFactory(new PropertyValueFactory<>("clientPrefHairColor"));
    activeStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activeStatus"));
    stateProvColumn.setCellValueFactory(new PropertyValueFactory<>("clientStateProv"));
    clientCountryColumn.setCellValueFactory(new PropertyValueFactory<>("clientCountry"));
    clientPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("clientZipCode"));

    String countryChoices[] = {"United States", "Canada", "Spain"};
    countryChoiceBox.setItems(FXCollections.observableArrayList(countryChoices));

    //Lambda listener used to listen for selection of fields and update fields as necessary
    clientTable.getSelectionModel().selectedItemProperty().addListener((origEntry, oldEntry, newEntry) -> {
        if (newEntry != null) {
            clientNameField.setText(newEntry.getClientName());
            emailField.setText(newEntry.getClientEmail());
            prefHairColorField.setText(newEntry.getClientPrefHairColor());
            activeStatusBox.setSelected(newEntry.getActiveStatus());
            countryChoiceBox.setValue(newEntry.getClientCountry());
            stateProvField.setText(newEntry.getClientStateProv());
            postalCodeField.setText(newEntry.getClientZipCode());
        }
    });

    }
    public void clearFields(){
        clientNameField.clear();
        emailField.clear();
        prefHairColorField.clear();
        activeStatusBox.setSelected(false);
        countryChoiceBox.getSelectionModel().clearSelection();
        stateProvField.clear();
        postalCodeField.clear();
    }
    public void addClient() throws SQLException {
        String clientName = clientNameField.getText();
        String clientEmail = emailField.getText();
        String clientPrefHairColor = prefHairColorField.getText();
        boolean clientActiveStatus = activeStatusBox.isSelected();
        String countryChoice = (String) countryChoiceBox.getSelectionModel().getSelectedItem();
        String stateProv = stateProvField.getText();
        String postalCode = postalCodeField.getText();
        clientModel client = new clientModel(clientModel.newClientId(), clientName, clientEmail, clientActiveStatus,
                clientPrefHairColor, stateProv, countryChoice, postalCode);
        clientQueries.addClient(client);
        clearFields();
        allClients.setAll(clientQueries.getClientList());
        clientTable.refresh();
    }
    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }
}
