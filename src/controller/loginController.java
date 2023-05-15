package src.controller;
import javafx.collections.ObservableList;
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
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import src.database.appointmentQueries;
import src.database.userQueries;
import src.model.appointmentsModel;
import src.model.userModel;
import src.utilities.logRecorder;
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

    /**
     * Button click that logs the user in after verifying the user name and password. Also, a check is used to notify if an
     * appointment is starting (or not starting) with 15 minutes.
     * @param click is the action event used. When the login button is clicked, the login process will commence.
     * @throws IOException
     * @throws SQLException
     */
    public void loginButtonClick(ActionEvent click) throws IOException, SQLException {
        //String startTimeQuery = "";
        Locale locale = Locale.getDefault();
        System.out.println(Locale.getDefault());
        String userName = userField.getText();
        String password = passField.getText();
        if (userName.isEmpty() || password.isEmpty()) {
            if (locale.equals(Locale.forLanguageTag("es-ES"))){
                utilityFunctions.warningAlert("El campo Usuario o Contraseña está vacío");
            }
            else {
                utilityFunctions.warningAlert("User or Password field is empty");
            }
            logRecorder.logRecord(userName, password, false);
        }
        else {
            if (userQueries.loginVerify(userName, password) > -1) {
                Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
                List<LocalDateTime> apptStartTimes = new ArrayList<>();
                List<String> onComingStartTimes = new ArrayList<>();
                boolean oncomingAppt = false;
                loginSuccess = new userModel(userName, password, userQueries.fetchUserId(userName));
                ObservableList<appointmentsModel> appointments = appointmentQueries.getAppointmentsList();
                for (appointmentsModel a : appointments) {
                    apptStartTimes.add(a.getApptStart());
                }
                for (appointmentsModel a : appointments) {
                    Timestamp start = Timestamp.valueOf(a.getApptStart());
                    if (Math.abs(start.getTime()-currentTime.getTime()) < TimeUnit.MINUTES.toMillis(15)) {
                        if (locale.equals(Locale.forLanguageTag("es-ES"))){
                            onComingStartTimes.add("Identificación de la aplicación "+
                                    a.getApptId()+", con la siguiente fecha y hora\nde inicio: "+ a.getApptStart()+
                                    ", comienza dentro de\nlos próximos 15 minutos.");
                        }
                        else {
                            onComingStartTimes.add("Appt ID "+a.getApptId()+", with the following start date and time: "+
                                    a.getApptStart()+", is starting within the next 15 minutes.");
                        }
                        oncomingAppt = true;
                    }

                }
                if (!oncomingAppt) {
                    if (locale.equals(Locale.forLanguageTag("es-ES"))){
                        utilityFunctions.warningAlert("No hay citas comenzando dentro de los próximos 15 minutos");
                    }
                    else {
                        utilityFunctions.warningAlert("No appointments are starting within the next 15 minutes");
                    }
                }
                else {
                    if (locale.equals(Locale.forLanguageTag("es-ES"))){
                        utilityFunctions.warningAlert("Hay citas a partir de los próximos 15 minutos:");
                        for (String apptData : onComingStartTimes) {
                            utilityFunctions.warningAlert(apptData);
                        }
                    }
                    else {
                        utilityFunctions.warningAlert("There are appointments starting within the next 15 minutes:");
                        for (String apptData : onComingStartTimes) {
                            utilityFunctions.warningAlert(apptData);
                        }
                    }
                }
                utilityFunctions.menuOpen(click, "../view/menu.fxml");
                logRecorder.logRecord(userName, password, true);
            }
            else {
                if (locale.equals(Locale.forLanguageTag("es-ES"))){
                    utilityFunctions.warningAlert("Credenciales no válidas");
                }
                else{
                    utilityFunctions.warningAlert("Invalid Credentials");
                }
                logRecorder.logRecord(userName, password,false);
            }
        }

    }

    /**
     * Closes the program when the user click on the exit button
     * @param click is the action event used. When the exit button is clicked, the connection will close.
     */
    public void exitButtonClick(ActionEvent click) {
        JDBC.closeConnection();
        Stage newStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
        newStage.close();
    }

}
