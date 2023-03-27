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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

}
