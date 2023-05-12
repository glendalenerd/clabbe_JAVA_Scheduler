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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static src.database.clientQueries.getClientAppts;

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
    @FXML
    public Label clientHeader;
    @FXML
    public Label clientNameLabel;
    @FXML
    public Label clientEmailLabel;
    @FXML
    public Label clientPrefHairColorLabel;
    @FXML
    public Label activeStatusLabel;
    @FXML
    public Label stateProvLabel;
    @FXML
    public Label countryLabel;
    @FXML
    public Label postalCodeLabel;



    /**
     * Lambda expression is used to incorporate a listener on the client that is selected within the table. Once a client
     * is selected, all text fields for the client will automatically be filled for the user with data from the client.
     * This is a feature that adds convenience for the user
     * @param url unused
     * @param resourceBundle unused
     */
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

    countryChoiceBox.setItems(FXCollections.observableArrayList(countryChoices));
    ResourceBundle languages = ResourceBundle.getBundle("src.Bundle", Locale.getDefault());
    clientHeader.setText(languages.getString("label.clientHeader"));
    clientNameLabel.setText(languages.getString("label.clientName"));
    clientEmailLabel.setText(languages.getString("label.clientEmail"));
    clientPrefHairColorLabel.setText(languages.getString("label.preferredHairColor"));
    activeStatusLabel.setText(languages.getString("label.activeStatus"));
    stateProvLabel.setText(languages.getString("label.stateProvince"));
    countryLabel.setText(languages.getString("label.country"));
    postalCodeLabel.setText(languages.getString("label.postalCode"));
    clientNameColumn.setText(languages.getString("label.clientName"));
    clientEmailColumn.setText(languages.getString("label.clientEmail"));
    hairColorColumn.setText(languages.getString("label.preferredHairColor"));
    activeStatusColumn.setText(languages.getString("label.activeStatus"));
    stateProvColumn.setText(languages.getString("label.stateProvince"));
    clientCountryColumn.setText(languages.getString("label.country"));
    clientPostalCodeColumn.setText(languages.getString("label.postalCode"));
    addButton.setText(languages.getString("button.addButton"));
    editButton.setText(languages.getString("button.editButton"));
    deleteButton.setText(languages.getString("button.deleteButton"));
    backButton.setText(languages.getString("button.backButton"));
    clearFieldsButton.setText(languages.getString("button.clearFieldsButton"));
    }

    /**
     * Used to clear all fields within the client screen
     */
    public void clearFields(){
        clientNameField.clear();
        emailField.clear();
        prefHairColorField.clear();
        activeStatusBox.setSelected(false);
        countryChoiceBox.getSelectionModel().clearSelection();
        stateProvField.clear();
        postalCodeField.clear();
    }

    /**
     * Takes all inputs provided from the user on the clients screen and creates a new client.
     * @throws SQLException SQL exception handler
     */
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

    /**
     * Takes all inputs provided from the user on the client screen and edits an existing client. Verifications are used
     * to make sure the client does not have existing appointments before deleting.
     * @throws SQLException SQL exception handler
     */
    public void editClient() throws SQLException {
        clientModel previousClient = clientTable.getSelectionModel().getSelectedItem();
        clientModel editClient = new clientModel(
                previousClient.getClientId(), clientNameField.getText(), emailField.getText(), activeStatusBox.isSelected(),
                prefHairColorField.getText(), stateProvField.getText(), (String) countryChoiceBox.getSelectionModel().getSelectedItem(),
                postalCodeField.getText()
        );
        if (activeStatusBox.isSelected() == false) {
            if (!clientQueries.getClientAppts(previousClient.getClientId()).isEmpty()) {
                utilityFunctions.warningAlert("Client's appointments must be deleted before setting to inactive");
                activeStatusBox.setSelected(true);
            }
            else {
                utilityFunctions.warningAlert("Client has been set to inactive");
                clientQueries.editClient(editClient);
                clearFields();
                allClients.setAll(clientQueries.getClientList());
                clientTable.refresh();
            }
        }
        else {
            clientQueries.editClient(editClient);
            clearFields();
            allClients.setAll(clientQueries.getClientList());
            clientTable.refresh();
        }
    }

    /**
     * Deletes an existing client, clears fields in the client screen and refreshes the client table
     * @throws SQLException SQL exception handler
     */
    public void deleteClient() throws SQLException {
        Integer selectedClientId = clientTable.getSelectionModel().getSelectedItem().getClientId();
        clientQueries.deleteClient(Integer.parseInt(String.valueOf(selectedClientId)));
        allClients.setAll(clientQueries.getClientList());
        clearFields();
        clientTable.refresh();
    }

    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }
}
