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
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main Menu Controller
 * @author Chris Labbe
 */
public class menuController implements Initializable {

    @FXML
    public Button clientsButton;
    @FXML
    public Button appointmentsButton;
    @FXML
    public Button reportsButton;
    @FXML
    public Button exitButton;
    @FXML
    public Label mainMenuText;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utilityFunctions.localeSet();
        ResourceBundle languages = ResourceBundle.getBundle("src.Bundle", Locale.getDefault());
        mainMenuText.setText(languages.getString("label.mainMenuText"));
        exitButton.setText(languages.getString("button.exit"));
        reportsButton.setText(languages.getString("button.reportsButton"));
        appointmentsButton.setText(languages.getString("button.appointmentsButton"));
        clientsButton.setText(languages.getString("button.clientsButton"));
    }

    /**
     * Navigates the user to the reports screen
     * @param click
     * @throws IOException
     */
    public void reportsMenu(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/reports.fxml");
    }

    /**
     * Navigates the user to the appointments screen
     * @param click
     * @throws IOException
     */
    public void appointmentsMenu(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/appointments.fxml");
    }

    /**
     * Navigates the user to the clients screen
     * @param click ActionEvent is used. When the user click on the Clients button, they are taken to the Clients screen.
     * @throws IOException
     */
    public void clientsMenu(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/clients.fxml");
    }

    /**
     * Closes the program from the main menu
     * @param click ActionEvent is used. When the user clicks on the exit button, the connection is closed.
     */
    public void exitButtonClick(ActionEvent click) {
        JDBC.closeConnection();
        Stage newStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        newStage.close();
    }

}
