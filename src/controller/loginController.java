package src.controller;
import src.database.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import src.database.userQueries;
import src.model.userModel;
import src.utilities.utilityFunctions;

/**
 * Controller for login menu
 * @author Chris Labbe
 */
public class loginController implements Initializable {
    public static userModel loginSuccess;
    @FXML
    private TextField passField;
    @FXML
    private TextField userField;
    @FXML
    private Button exitButton;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Label loginTitle;
    @FXML
    private Button loginButton;
    @FXML
    private Label locationLabel;
    @FXML
    private Label userLocation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utilityFunctions.localeSet();
        ZoneId currentZone = utilityFunctions.fetchZoneId();
        userLocation.setText(String.valueOf(currentZone));
        ResourceBundle languages = ResourceBundle.getBundle("src.Bundle", Locale.getDefault());
        userLabel.setText(languages.getString("label.username"));
        loginTitle.setText((languages.getString("label.login")));
        locationLabel.setText(languages.getString("label.location"));
        passwordLabel.setText(languages.getString("label.password"));
        loginButton.setText(languages.getString("button.login"));
        exitButton.setText(languages.getString("button.exit"));
    }

    public void loginButtonClick(ActionEvent click) throws IOException, SQLException {
        String userName = userField.getText();
        String password = passField.getText();
        if (userName.isEmpty() || password.isEmpty()) {
            utilityFunctions.warningAlert("User or Password field is empty");
        }
        else if (userQueries.loginVerify(userName, password) > -1) {
            boolean oncomingAppt = false;
            loginSuccess = new userModel(userName, password, userQueries.fetchUserId(userName));
            //List<Integer> currentAppts = userQueries.
        }
        utilityFunctions.menuOpen(click, "../view/menu.fxml");

    }
    public void exitButtonClick(ActionEvent click) {
        JDBC.closeConnection();
        Stage newStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        newStage.close();
    }

}
