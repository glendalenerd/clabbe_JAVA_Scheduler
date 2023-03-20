package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.database.appointmentQueries;
import src.model.appointmentsModel;
import src.model.clientModel;
import src.utilities.utilityFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class appointmentsController implements Initializable{
    private final ObservableList<appointmentsModel> allAppointments = FXCollections.observableArrayList();
    @FXML
    public TableView<appointmentsModel> apptTable;
    @FXML
    public TableColumn<appointmentsModel, Integer> apptIdColumn;
    @FXML
    public TableColumn<appointmentsModel, String> apptTitleColumn;
    @FXML
    public TableColumn<appointmentsModel, String> apptDescColumn;
    @FXML
    public TableColumn<appointmentsModel, String> apptLocationColumn;
    @FXML
    public TableColumn<appointmentsModel, String> apptStylistColumn;
    @FXML
    public TableColumn<appointmentsModel, String> apptTypeColumn;
    @FXML
    public TableColumn<appointmentsModel, LocalDateTime> apptStartDateColumn;
    @FXML
    public TableColumn<appointmentsModel, LocalDateTime> apptEndDateColumn;
    @FXML TableColumn<appointmentsModel, Integer> apptClientIdColumn;
    @FXML
    public TextField apptIdField;
    @FXML
    public TextField apptTitleField;
    @FXML
    public TextField apptDescField;
    @FXML
    public TextField apptLocationField;
    @FXML
    public ChoiceBox apptStylistField;
    @FXML
    public TextField apptTypeField;
    @FXML
    public DatePicker apptStartDatePicker;
    @FXML
    public ChoiceBox aptStartHourField;
    @FXML
    public ChoiceBox aptStartMinuteField;
    @FXML
    public DatePicker apptEndDatePicker;
    @FXML
    public ChoiceBox apptEndHourField;
    @FXML
    public ChoiceBox apptEndMinuteField;
    @FXML
    public Button backButton;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allAppointments.setAll(appointmentQueries.getAppointmentsList());
        } catch (SQLException i) {
            throw new RuntimeException(i);
        }
    apptTable.setItems(allAppointments);
    apptTable.refresh();
    apptIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptId"));
    apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
    apptDescColumn.setCellValueFactory(new PropertyValueFactory<>("apptDesc"));
    apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
    apptStylistColumn.setCellValueFactory(new PropertyValueFactory<>("apptStylist"));
    apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("apptType"));
    apptStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
    apptEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
    apptClientIdColumn.setCellValueFactory(new PropertyValueFactory<>("apptClientId"));
    }

    public void newAppointment() throws SQLException {
        if (apptIdField.getText().isEmpty()){

        }
    }
    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }
}
