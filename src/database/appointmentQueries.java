package src.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.model.appointmentsModel;
import src.model.clientModel;
import src.utilities.utilityFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class appointmentQueries {
    public static ObservableList<appointmentsModel> getAppointmentsList() throws SQLException {
        ObservableList<appointmentsModel> appointmentsListAll = FXCollections.observableArrayList();
        String appointmentsQuery = "SELECT * FROM appt";
        ResultSet appointmentsFetch = utilityFunctions.DBQuery(appointmentsQuery);
        while (appointmentsFetch.next()) {
            int apptId = appointmentsFetch.getInt("idappt");
            String apptTitle = appointmentsFetch.getString("titlle");
            String apptDesc = appointmentsFetch.getString("descr");
            String apptLocation = appointmentsFetch.getString("location");
            String apptStylistId = appointmentsFetch.getString("stylid");
            String apptType = appointmentsFetch.getString("type");
            LocalDateTime apptStart = appointmentsFetch.getTimestamp("start").toLocalDateTime();
            LocalDateTime apptEnd = appointmentsFetch.getTimestamp("end").toLocalDateTime();
            int apptClientId = appointmentsFetch.getInt("clientid");
            appointmentsModel appointment = new appointmentsModel(apptId, apptTitle, apptDesc, apptLocation, apptStylistId,
                    apptType, apptStart, apptEnd, apptClientId);
            appointmentsListAll.add(appointment);

        }
        return appointmentsListAll;
    }
    public static void addAppointment(appointmentsModel appointment) throws SQLException {
        Integer apptId = appointment.getApptId();
        String apptTitle = appointment.getApptTitle();
        String apptDesc = appointment.getApptDesc();
        String apptLocation = appointment.getApptLocation();
        String apptStylist = appointment.getApptStylist();
        String apptType = appointment.getApptType();
        //LocalDateTime apptStart = appointment.getApptStart();
        //LocalDateTime apptEnd = appointment.getApptEnd();
        int apptClientId = appointment.getApptClientId();
        String queryCommand = "INSERT INTO appt VALUES ";
        String queryText = apptId+", '"+apptTitle+"', '"+apptDesc+"', '"+apptLocation+"', '"+apptStylist+
                "', '"+apptType+"', '"+appointment.getApptStart()+"', '"+appointment.getApptEnd()+"', "+apptClientId;
        System.out.println(queryCommand+"("+queryText+");");
        utilityFunctions.DBExec(queryCommand+"("+queryText+");");
    }
}
