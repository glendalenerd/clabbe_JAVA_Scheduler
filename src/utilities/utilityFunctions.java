package src.utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.ZoneId;
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


}
