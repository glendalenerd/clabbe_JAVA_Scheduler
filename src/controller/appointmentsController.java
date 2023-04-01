package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.database.appointmentQueries;
import src.database.stylistQueries;
import src.model.appointmentsModel;
import src.model.clientModel;
import src.model.stylistModel;
import src.utilities.utilityFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import static src.utilities.utilityFunctions.database;

public class appointmentsController implements Initializable{
    private final ObservableList<appointmentsModel> allAppointments = FXCollections.observableArrayList();
    private final ObservableList<stylistModel> stylistChoices = FXCollections.observableArrayList();
    //private final ArrayList<String> stylistNamesList = new ArrayList<>();
    private final ArrayList<Integer> stylistIdsList = new ArrayList<>();
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
    public TextField apptClientIdField;
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
    @FXML
    public Button addButton;
    @FXML
    public Button editButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button clearFieldsButton;
    @FXML
    public RadioButton weekRadioButton;
    @FXML
    public RadioButton monthRadioButton;
    @FXML
    public RadioButton allRadioButton;
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

    //Lambda listener used to listen for selection of fields and update fields as necessary
    apptTable.getSelectionModel().selectedItemProperty().addListener((origEntry, oldEntry, newEntry) -> {
        if (newEntry != null) {
            apptTitleField.setText(newEntry.getApptTitle());
            apptDescField.setText(newEntry.getApptDesc());
            apptLocationField.setText(newEntry.getApptLocation());
            apptClientIdField.setText(Integer.toString(newEntry.getApptClientId()));
            apptStylistField.setValue(newEntry.getApptStylist());
            apptTypeField.setText(newEntry.getApptType());
            apptStartDatePicker.setValue(LocalDate.from(newEntry.getApptStart()));
            apptEndDatePicker.setValue(LocalDate.from(newEntry.getApptEnd()));
            aptStartMinuteField.setValue(newEntry.getApptStart().getMinute());
            aptStartHourField.setValue(newEntry.getApptStart().getHour());
            apptEndMinuteField.setValue(newEntry.getApptEnd().getMinute());
            apptEndHourField.setValue(newEntry.getApptEnd().getHour());
        }
    });

    List<Integer> hourChoices = IntStream.rangeClosed(0, 23).boxed().toList();
    List<Integer> minuteChoices = IntStream.rangeClosed(0, 59).boxed().toList();
    aptStartHourField.getItems().addAll(hourChoices);
    aptStartMinuteField.getItems().addAll(minuteChoices);
    apptEndHourField.getItems().addAll(hourChoices);
    apptEndMinuteField.getItems().addAll(minuteChoices);
        //try {
            //stylistChoices.setAll(stylistQueries.getStylistList());
            //for (stylistModel s : stylistChoices) {
                //stylistNamesList.add(s.getStylistName());
                //System.out.println(s.getStylistId());
            //}
                //apptStylistField.getItems().addAll(stylistNamesList);
        //} catch (SQLException e) {
            //throw new RuntimeException(e);
        //}
        try {
            stylistChoices.setAll(stylistQueries.getStylistList());
            for (stylistModel s : stylistChoices) {
                stylistIdsList.add(s.getStylistId());
                //System.out.println(s.getStylistId());
            }
            apptStylistField.getItems().addAll(stylistIdsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void clearFields() {
        apptIdField.clear();
        apptTitleField.clear();
        apptDescField.clear();
        apptLocationField.clear();
        apptClientIdField.clear();
        apptStylistField.getSelectionModel().clearSelection();
        apptTypeField.clear();
        apptStartDatePicker.setValue(null);
        apptEndDatePicker.setValue(null);
        aptStartHourField.getSelectionModel().clearSelection();
        aptStartMinuteField.getSelectionModel().clearSelection();
        apptEndHourField.getSelectionModel().clearSelection();
        apptEndMinuteField.getSelectionModel().clearSelection();
    }

    public void weekRadioFilter() throws SQLException {
        allRadioButton.setSelected(false);
        monthRadioButton.setSelected(false);
        ObservableList<appointmentsModel> apptsWeek = FXCollections.observableArrayList();
        LocalDateTime nowPlusWeek = LocalDateTime.now().plusWeeks(1);
        LocalDateTime now = LocalDateTime.now();
        allAppointments.setAll(appointmentQueries.getAppointmentsList());
        for (appointmentsModel appointment : allAppointments) {
            if (appointment.getApptStart().isBefore(nowPlusWeek) && appointment.getApptStart().isAfter(now)) {
                apptsWeek.add(appointment);
            }
        }
        apptTable.setItems(apptsWeek);
        apptTable.refresh();
    }
    public void monthRadioFilter() throws SQLException {
        weekRadioButton.setSelected(false);
        allRadioButton.setSelected(false);
        ObservableList<appointmentsModel> apptsMonth = FXCollections.observableArrayList();
        LocalDateTime nowPlusMonth = LocalDateTime.now().plusMonths(1);
        LocalDateTime now = LocalDateTime.now();
        allAppointments.setAll(appointmentQueries.getAppointmentsList());
        for (appointmentsModel appointment : allAppointments) {
            if (appointment.getApptStart().isBefore(nowPlusMonth) && appointment.getApptStart().isAfter(now)) {
                apptsMonth.add(appointment);
            }
        }
        apptTable.setItems(apptsMonth);
        apptTable.refresh();
    }
    public void allRadioFilter() throws SQLException {
        weekRadioButton.setSelected(false);
        monthRadioButton.setSelected(false);
        allAppointments.setAll(appointmentQueries.getAppointmentsList());
        apptTable.setItems(allAppointments);
        apptTable.refresh();
    }

    public void newAppointment() throws SQLException {
        String apptTitle = apptTitleField.getText();
        String apptDesc = apptDescField.getText();
        String apptLocation = apptLocationField.getText();
        Integer apptStylist = (Integer) apptStylistField.getSelectionModel().getSelectedItem();
        String apptType = apptTypeField.getText();
        Integer apptClientId = Integer.valueOf(apptClientIdField.getText());
        System.out.println(apptClientId);
        LocalDate localDateStart = apptStartDatePicker.getValue();
        LocalDate localDateEnd = apptEndDatePicker.getValue();
        LocalTime localTimeStart = LocalTime.of((Integer) aptStartHourField.getValue(), (Integer) aptStartMinuteField.getValue());
        LocalTime localTimeEnd = LocalTime.of((Integer) apptEndHourField.getValue(), (Integer) apptEndMinuteField.getValue());
        LocalDateTime completeStart = LocalDateTime.of(localDateStart, localTimeStart);
        LocalDateTime completeEnd = LocalDateTime.of(localDateEnd, localTimeEnd);
        appointmentsModel appointment = new appointmentsModel(appointmentsModel.newApptId(), apptTitle, apptDesc, apptLocation,
                apptType, completeStart, completeEnd, apptClientId, apptStylist);
        src.database.appointmentQueries.addAppointment(appointment);
        clearFields();
        allAppointments.setAll(appointmentQueries.getAppointmentsList());
        apptTable.refresh();

    }
    public void editAppointment() throws SQLException {
        appointmentsModel previousAppt = apptTable.getSelectionModel().getSelectedItem();
        String apptTitle = apptTitleField.getText();
        String apptDesc = apptDescField.getText();
        String apptLocation = apptLocationField.getText();
        Integer apptStylist = (Integer) apptStylistField.getSelectionModel().getSelectedItem();
        String apptType = apptTypeField.getText();
        Integer apptClientId = Integer.valueOf(apptClientIdField.getText());
        System.out.println(apptClientId);
        LocalDate localDateStart = apptStartDatePicker.getValue();
        LocalDate localDateEnd = apptEndDatePicker.getValue();
        LocalTime localTimeStart = LocalTime.of((Integer) aptStartHourField.getValue(), (Integer) aptStartMinuteField.getValue());
        LocalTime localTimeEnd = LocalTime.of((Integer) apptEndHourField.getValue(), (Integer) apptEndMinuteField.getValue());
        LocalDateTime completeStart = LocalDateTime.of(localDateStart, localTimeStart);
        LocalDateTime completeEnd = LocalDateTime.of(localDateEnd, localTimeEnd);
        appointmentsModel editAppt = new appointmentsModel(
                previousAppt.getApptId(), apptTitle, apptDesc, apptLocation,
                apptType, completeStart, completeEnd, apptClientId, apptStylist
        );
        appointmentQueries.editAppointment(editAppt);
        clearFields();
        allAppointments.setAll(appointmentQueries.getAppointmentsList());
        apptTable.refresh();
    }
    public void deleteAppointment() throws SQLException {
        appointmentsModel selectedAppts = apptTable.getSelectionModel().getSelectedItem();
        Integer apptToDelete = selectedAppts.getApptId();
        String deleteCommand = "DELETE FROM appt WHERE idappt="+apptToDelete;
        utilityFunctions.DBExec(deleteCommand);
        allAppointments.setAll(appointmentQueries.getAppointmentsList());
        clearFields();
        apptTable.refresh();
    }
    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }
}
