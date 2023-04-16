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
            int apptStylistId = Integer.parseInt(appointmentsFetch.getString("stylid"));
            String apptType = appointmentsFetch.getString("type");
            LocalDateTime apptStart = appointmentsFetch.getTimestamp("start").toLocalDateTime();
            LocalDateTime apptEnd = appointmentsFetch.getTimestamp("end").toLocalDateTime();
            int apptClientId = appointmentsFetch.getInt("clientid");
            appointmentsModel appointment = new appointmentsModel(apptId, apptTitle, apptDesc, apptLocation,
                    apptType, apptStart, apptEnd, apptClientId, apptStylistId);
            appointmentsListAll.add(appointment);

        }
        return appointmentsListAll;
    }
    public static void addAppointment(appointmentsModel appointment) throws SQLException {
        Integer apptId = appointment.getApptId();
        String apptTitle = appointment.getApptTitle();
        String apptDesc = appointment.getApptDesc();
        String apptLocation = appointment.getApptLocation();
        Integer apptStylist = appointment.getApptStylist();
        String apptType = appointment.getApptType();
        //LocalDateTime apptStart = appointment.getApptStart();
        //LocalDateTime apptEnd = appointment.getApptEnd();
        int apptClientId = appointment.getApptClientId();
        String queryCommand = "INSERT INTO appt VALUES ";
        String queryText = apptId+", '"+apptTitle+"', '"+apptDesc+"', '"+apptLocation+"', '"+apptType+"', " +
                "'"+appointment.getApptStart()+"', " +
                "'"+appointment.getApptEnd()+"', " +
                "'"+apptClientId+"', '"+apptStylist+"'";
        //System.out.println(queryCommand+"("+queryText+");");
        utilityFunctions.DBExec(queryCommand+"("+queryText+");");
    }
    public static void editAppointment(appointmentsModel appointment) throws SQLException {
        String queryCommand = "UPDATE appt SET ";
        String queryText = "titlle = '"+appointment.getApptTitle()+"', descr = '"+appointment.getApptDesc()+"', location = '"+
                appointment.getApptLocation()+"', type = '"+appointment.getApptType()+"', start = '"+
                appointment.getApptStart()+"', end = '"+appointment.getApptEnd()+"', clientid = '"+
                appointment.getApptClientId()+"', stylid = '"+appointment.getApptClientId()+"'"+
                " WHERE idappt = "+appointment.getApptId();
        //System.out.println(queryCommand+queryText);
        utilityFunctions.DBExec(queryCommand+queryText);
    }

}