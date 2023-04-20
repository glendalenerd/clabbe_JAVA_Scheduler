package src.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    public Button backButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            reportForAppointments();
            reportForStylist();
            reportForClients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void menuReturn(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/menu.fxml");
    }

    /**
     * Report that breaks down clients per country
     * @throws SQLException
     */
    public void reportForClients() throws SQLException {
        String canadaCountryQuery = "SELECT DISTINCT name FROM client WHERE country = 'Canada'";
        String uSCountryQuery = "SELECT DISTINCT name FROM client WHERE country = 'United States'";
        String spainCountryQuery = "SELECT DISTINCT name FROM client WHERE country = 'Spain'";
        ResultSet canadaReportQuery = utilityFunctions.DBQuery(canadaCountryQuery);
        clientsPerCountry.appendText("Clients that reside in Canada:");
        clientsPerCountry.appendText("\n");
        while (canadaReportQuery.next()) {
            clientsPerCountry.appendText(canadaReportQuery.getString("name"));
            clientsPerCountry.appendText("\n");
        }
        clientsPerCountry.appendText("\n");
        ResultSet uSReportQuery = utilityFunctions.DBQuery(uSCountryQuery);
        clientsPerCountry.appendText("Clients that reside in the United States:");
        clientsPerCountry.appendText("\n");
        while (uSReportQuery.next()) {
            clientsPerCountry.appendText(uSReportQuery.getString("name"));
            clientsPerCountry.appendText("\n");
        }
        clientsPerCountry.appendText("\n");
        ResultSet spainReportQuery = utilityFunctions.DBQuery(spainCountryQuery);
        clientsPerCountry.appendText("Clients that reside in Spain:");
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
        String apptSelectQuery = "SELECT count(*) as 'Number', week(start) AS 'Week',type AS 'Type' FROM appt " +
                "GROUP BY start,type;";
        System.out.println(apptSelectQuery);
        ResultSet apptReportQuery = utilityFunctions.DBQuery(apptSelectQuery);
        while (apptReportQuery.next()) {
            String count = "Count: "+apptReportQuery.getString("Number");
            String week = "Week: "+apptReportQuery.getString("Week");
            String type = "Type: "+apptReportQuery.getString("Number");
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
        stylistList = stylistQueries.getStylistList();
        for (stylistModel stylist : stylistList) {
            stylistIdsList.add(stylist.getStylistId());
        }
        for (Integer stylistId : stylistIdsList) {
            stylistSchedules.appendText("\n");
            String apptTitleText = "Appointment Schedule For ";
            String stylistName = stylistQueries.getStylistName(stylistId);
            stylistSchedules.appendText(apptTitleText+stylistName+":");
            stylistSchedules.appendText("\n");
            String stylistInfoQuery = "SELECT idappt, titlle, descr, location, type, start, end, clientid " +
                    "FROM appt WHERE stylid = ";
            ResultSet stylistAppts = utilityFunctions.DBQuery(stylistInfoQuery+stylistId);
            while (stylistAppts.next()) {
                String apptId = "Appt ID: " + stylistAppts.getString("idappt");
                String title = "Title: " + stylistAppts.getString("titlle");
                String description = "Description: " + stylistAppts.getString("descr");
                String location = "Location: " + stylistAppts.getString("location");
                String type = "Type: " + stylistAppts.getString("type");
                String start = "Start: " + stylistAppts.getString("start");
                String end = "End: " + stylistAppts.getString("end");
                String clientId = "Client ID: "+stylistAppts.getString("clientid");
                //stylistSchedules.appendText("-------");
                //stylistSchedules.appendText("\n");
                stylistSchedules.appendText(String.format("%-20s%-30s%-35s%-30s%-35s%-35s%-35s%-35s", apptId, title,
                        description, location, type, start, end, clientId));
                stylistSchedules.appendText("\n");
            }
        }
    }

}
