package src.utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import src.database.JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.Optional;

public class utilityFunctions {

    /**
     * Takes string text and displays that text in an alert message as a warning
     * @param text
     */
    public static void warningAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(text);
        alert.showAndWait();
    }
    public static LocalDateTime getLocalBusTime(LocalDateTime universal) {
        ZonedDateTime universalZoned = universal.atZone(getZoneId());
        return universalZoned.toLocalDateTime().atZone(ZoneId.of("UTC")).
                withZoneSameInstant(ZoneId.of("US/Mountain")).toLocalDateTime();
    }

    public static LocalDateTime getUTCTime(LocalDateTime timeAndDate){
        ZonedDateTime timeDateZone = timeAndDate.atZone(getZoneId());
        ZonedDateTime universalZone = timeDateZone.withZoneSameInstant(ZoneId.of("UTC"));
        return universalZone.toLocalDateTime();
    }
    public static ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    public static LocalDateTime getUniversalTime(LocalDateTime localClock) {
        ZonedDateTime localClockZoned = localClock.atZone(getZoneId());
        ZonedDateTime UniversalZoned = localClockZoned.withZoneSameInstant(ZoneId.of("UTC"));
        return UniversalZoned.toLocalDateTime();
    }

    public static boolean confirmationAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static void localeSet() {
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
    }

    public static ZoneId fetchZoneId () {
        return ZoneId.systemDefault();
    }

    public static void menuOpen(ActionEvent event, String freshMenu) throws IOException {
        Parent parent = FXMLLoader.load(utilityFunctions.class.getResource(freshMenu));
        Scene newScene = new Scene(parent);
        Stage newWindow = (Stage) ((Node)event.getSource()).getScene().getWindow();
        newWindow.setScene(newScene);
        newWindow.show();
    }
    public static final Connection database = JDBC.getConnection();

    public static void DBExec(String dbQuery) throws SQLException {
        PreparedStatement ps = database.prepareStatement(dbQuery);
        ps.execute();
    }
    public static ResultSet DBQuery(String dbQuery) throws SQLException {
        PreparedStatement ps = database.prepareStatement(dbQuery);
        ResultSet rs;
        return rs = ps.executeQuery();
    }

    public static boolean businessHourCheck(LocalDateTime completeStart, LocalDateTime completeEnd) {
        LocalDateTime universalStart = utilityFunctions.getUniversalTime(completeStart);
        LocalDateTime universalEnd = utilityFunctions.getUniversalTime(completeEnd);
        LocalDateTime businessTimeStart = utilityFunctions.getLocalBusTime(universalStart);
        LocalDateTime businessTimeEnd = utilityFunctions.getLocalBusTime(universalEnd);
        LocalTime officeOpenTime = LocalTime.of(8, 0,0);
        LocalTime officeCloseTime = LocalTime.of(22,0,0);
        if (businessTimeStart.toLocalTime().isBefore(officeOpenTime)) {
            utilityFunctions.warningAlert("This Appointment Falls Outside of Regular Hours of 8AM to 10PM MST");
            return false;
        }
        return true;
    }

}
