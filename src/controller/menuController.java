package src.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import src.database.JDBC;
import src.utilities.utilityFunctions;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

abstract class menuUnit {
    abstract void navigate(ActionEvent click) throws IOException;
}
class reportsMenuUnit extends menuUnit {

    /**
     * Navigates the user to the reports screen
     * @param click ActionEvent is used. When the user clicks on the reports button, they are taken to the reports screen.
     * @throws IOException
     */
    void navigate(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/reports.fxml");
    }
}
class appointmentsMenuUnit extends menuUnit {

    /**
     * Navigates the user to the appointments screen
     * @param click ActionEvent is used. When the user clicks on the appts button, they are taken to the appts screen.
     * @throws IOException
     */
    void navigate(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/appointments.fxml");
    }
}

class clientsMenuUnit extends menuUnit {

    /**
     * Navigates the user to the clients screen
     * @param click ActionEvent is used. When the user clicks on the Clients button, they are taken to the Clients screen.
     * @throws IOException
     */
    void navigate(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/clients.fxml");
    }
}

/**
 * Main Menu Controller
 * @author Chris Labbe
 */
public class menuController implements Initializable {
    @FXML
    private Button clientsButton;
    @FXML
    private Button appointmentsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label mainMenuText;

    private menuUnit reportsMenuUnit = new reportsMenuUnit();
    private menuUnit appointmentsMenuUnit = new appointmentsMenuUnit();
    private menuUnit clientsMenuUnit = new clientsMenuUnit();

    private String getLocalizedString(String key) {
        ResourceBundle languages = ResourceBundle.getBundle("src.Bundle", Locale.getDefault());
        return languages.getString(key);
    }

    private void initializeUi() {
        reportsButton.setText(getLocalizedString("button.reportsButton"));
        appointmentsButton.setText(getLocalizedString("button.appointmentsButton"));
        clientsButton.setText(getLocalizedString("button.clientsButton"));
        mainMenuText.setText(getLocalizedString("label.mainMenuText"));
        exitButton.setText(getLocalizedString("button.exit"));
    }

    private void setLocale() {
        utilityFunctions.localeSet();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeUi();
        setLocale();
    }
    @FXML
    private void reportsMenu(ActionEvent click) throws IOException {
        reportsMenuUnit.navigate(click);
    }

    @FXML
    private void appointmentsMenu(ActionEvent click) throws IOException {
        appointmentsMenuUnit.navigate(click);
    }

    @FXML
    private void clientsMenu(ActionEvent click) throws IOException {
        clientsMenuUnit.navigate(click);
    }

    /**
     * Closes the program from the main menu
     * @param click ActionEvent is used. When the user clicks on the exit button, the connection is closed.
     */
    @FXML
    private void exitButtonClick(ActionEvent click) {
        JDBC.closeConnection();
        Stage currentStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
