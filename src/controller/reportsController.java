package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import src.database.clientQueries;
import src.database.stylistQueries;
import src.model.appointmentsModel;
import src.model.clientModel;
import src.model.stylistModel;
import src.utilities.utilityFunctions;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class reportsController implements Initializable {
    public ObservableList<clientModel> clientListUs = FXCollections.observableArrayList();
    public ObservableList<clientModel> clientListCanada = FXCollections.observableArrayList();
    public ObservableList<clientModel> clientListSpain = FXCollections.observableArrayList();
    public ObservableList<stylistModel> stylistList = FXCollections.observableArrayList();
    public ObservableList<appointmentsModel> apptList = FXCollections.observableArrayList();
    private ArrayList<Integer> stylistIdsList = new ArrayList<>();
    private ArrayList<Integer> clientIdsList = new ArrayList<>();
    @FXML
    public TextArea clientApptsText;
    @FXML
    public TextArea stylistSchedules;
    @FXML
    public TextArea clientsPerCountry;
    @FXML
    public Tab clientApptsTab;
    @FXML
    public Tab stylistSchedulesTab;
    @FXML
    public Tab clientsPerCountryTab;
    @FXML
    public Button backButton;
    @FXML
    public Label reportsHeaderLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reportForAppointments();
            reportForStylist();
            reportForClients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResourceBundle languages = ResourceBundle.getBundle("src.Bundle", Locale.getDefault());
        reportsHeaderLabel.setText(languages.getString("label.reportsHeader"));
        clientApptsTab.setText(languages.getString("tab.clientApptsTab"));
        stylistSchedulesTab.setText(languages.getString("tab.stylistSchedules"));
        clientsPerCountryTab.setText(languages.getString("tab.clientsPerCountry"));
        backButton.setText(languages.getString("button.backButton"));
    }
    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }

    /**
     * Report that breaks down clients per country
     * @throws SQLException
     */
    public void reportForClients() throws SQLException {
        Locale locale = Locale.getDefault();
        String canadaCountryQuery = "SELECT DISTINCT name FROM client WHERE country = 'Canada'";
        String uSCountryQuery = "SELECT DISTINCT name FROM client WHERE country = 'United States'";
        String spainCountryQuery = "SELECT DISTINCT name FROM client WHERE country = 'Spain'";
        ResultSet canadaReportQuery = utilityFunctions.DBQuery(canadaCountryQuery);
        String canadaText = "Clients that reside in Canada:";
        String unitedStatesText = "Clients that reside in the United States:";
        String spainText = "Clients that reside in Spain:";
        if (locale.equals(Locale.forLanguageTag("es-ES"))){
            canadaText = "Clientes que residen en Canadá:";
            unitedStatesText = "Clientes que residen en los Estados Unidos:";
            spainText = "Clientes que residen en España:";
        }
        clientsPerCountry.appendText(canadaText);
        clientsPerCountry.appendText("\n");
        while (canadaReportQuery.next()) {
            clientsPerCountry.appendText(canadaReportQuery.getString("name"));
            clientsPerCountry.appendText("\n");
        }
        clientsPerCountry.appendText("\n");
        ResultSet uSReportQuery = utilityFunctions.DBQuery(uSCountryQuery);
        clientsPerCountry.appendText(unitedStatesText);
        clientsPerCountry.appendText("\n");
        while (uSReportQuery.next()) {
            clientsPerCountry.appendText(uSReportQuery.getString("name"));
            clientsPerCountry.appendText("\n");
        }
        clientsPerCountry.appendText("\n");
        ResultSet spainReportQuery = utilityFunctions.DBQuery(spainCountryQuery);
        clientsPerCountry.appendText(spainText);
        clientsPerCountry.appendText("\n");
        while (spainReportQuery.next()) {
            clientsPerCountry.appendText(spainReportQuery.getString("name"));
            clientsPerCountry.appendText("\n");
        }
        //clientsPerCountry.appendText("\n");
    }

    /**
     * Report that displays number of appointments by type
     * @throws SQLException
     */
    public void reportForAppointments() throws SQLException {
        Locale locale = Locale.getDefault();
        String countText = "Count: ";
        String weekText = "Week: ";
        String typeText = "Type: ";
        if (locale.equals(Locale.forLanguageTag("es-ES"))){
            countText = "Contar: ";
            weekText = "Semana: ";
            typeText = "Tipo: ";
        }
        String apptSelectQuery = "SELECT count(*) as 'Number', week(start) AS 'Week',type AS 'Type' FROM appt " +
                "GROUP BY start,type;";
        ResultSet apptReportQuery = utilityFunctions.DBQuery(apptSelectQuery);
        while (apptReportQuery.next()) {
            String count = countText+apptReportQuery.getString("Number");
            String week = weekText+apptReportQuery.getString("Week");
            String type = typeText+apptReportQuery.getString("Type");
            clientApptsText.appendText("-------");
            clientApptsText.appendText("\n");
            clientApptsText.appendText(String.format("%-25s%-25s%-25s", count, week, type));
            clientApptsText.appendText("\n");
        }
    }

    /**
     * Report that displays appointment schedules for stylists
     * @throws SQLException
     */
    public void reportForStylist() throws SQLException {
        Locale locale = Locale.getDefault();
        String apptTitleText = "Appointment Schedule For ";
        String apptIdText = "Appt ID: ";
        String titleText = "Title: ";
        String descriptionText = "Description: ";
        String locationText = "Location: ";
        String typeText = "Type: ";
        String startText = "Start: ";
        String endText = "End: ";
        String clientIdText = "Client ID: ";
        if (locale.equals(Locale.forLanguageTag("es-ES"))){
            apptTitleText = "Calendario de citas para ";
            apptIdText = "ID de la cita: ";
            titleText = "Título: ";
            descriptionText = "Descripción: ";
            locationText = "Ubicación: ";
            typeText = "Tipo: ";
            startText = "Comenzar: ";
            endText = "Fin: ";
            clientIdText = "Identificación del cliente: ";
        }
        stylistList = stylistQueries.getStylistList();
        for (stylistModel stylist : stylistList) {
            stylistIdsList.add(stylist.getStylistId());
        }
        for (Integer stylistId : stylistIdsList) {
            stylistSchedules.appendText("\n");
            String stylistName = stylistQueries.getStylistName(stylistId);
            stylistSchedules.appendText(apptTitleText+stylistName+":");
            stylistSchedules.appendText("\n");
            String stylistInfoQuery = "SELECT idappt, titlle, descr, location, type, start, end, clientid " +
                    "FROM appt WHERE stylid = ";
            ResultSet stylistAppts = utilityFunctions.DBQuery(stylistInfoQuery+stylistId);
            while (stylistAppts.next()) {
                String apptId = apptIdText + stylistAppts.getString("idappt");
                String title = titleText + stylistAppts.getString("titlle");
                String description = descriptionText + stylistAppts.getString("descr");
                String location = locationText + stylistAppts.getString("location");
                String type = typeText + stylistAppts.getString("type");
                String start = startText + stylistAppts.getString("start");
                String end = endText + stylistAppts.getString("end");
                String clientId = clientIdText +stylistAppts.getString("clientid");
                //stylistSchedules.appendText("-------");
                //stylistSchedules.appendText("\n");
                stylistSchedules.appendText(String.format("%-20s%-30s%-35s%-30s%-35s%-35s%-35s%-35s", apptId, title,
                        description, location, type, start, end, clientId));
                stylistSchedules.appendText("\n");
            }
        }
    }

}
