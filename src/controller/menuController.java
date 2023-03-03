package src.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    public void reportsMenu(ActionEvent click) throws IOException {
        utilityFunctions.menuOpen(click, "../view/reports.fxml");
    }

}
