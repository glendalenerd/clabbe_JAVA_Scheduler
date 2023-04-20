package src.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.database.JDBC;
import src.utilities.utilityFunctions;

import java.io.IOException;

/**
 * Main Menu Controller
 * @author Chris Labbe
 */
public class menuController {

    @FXML
    public Button clientsButton;
    @FXML
    public Button appointmentsButton;
    @FXML
    public Button reportsButton;
    @FXML
    public Button exitButton;

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
     * @param click
     * @throws IOException
     */
    public void clientsMenu(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/clients.fxml");
    }

    /**
     * Closes the program from the main menu
     * @param click
     */
    public void exitButtonClick(ActionEvent click) {
        JDBC.closeConnection();
        Stage newStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        newStage.close();
    }

}
