package src.model;

import javafx.collections.ObservableList;
import src.database.appointmentQueries;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class appointmentsModel {
    public int apptId;
    public String apptTitle;
    public String apptDesc;
    public String apptLocation;
    public String apptType;
    public LocalDateTime apptStart;
    public LocalDateTime apptEnd;
    public int apptClientId;
    public int apptStylist;

    public appointmentsModel(int apptId, String apptTitle, String apptDesc, String apptLocation, String apptType,
                             LocalDateTime apptStart, LocalDateTime apptEnd, int apptClientId, int apptStylist) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptClientId = apptClientId;
        this.apptStylist = apptStylist;
    }
    public int getApptId() { return apptId; }
    public String getApptTitle() { return apptTitle; }
    public String getApptDesc() { return apptDesc; }
    public String getApptLocation() {return apptLocation; }
    public Integer getApptStylist() {return apptStylist; }
    public String getApptType() {return apptType; }
    public LocalDateTime getApptStart() {return apptStart; }
    public LocalDateTime getApptEnd() {return apptEnd; }
    public int getApptClientId() {return apptClientId; }

    public static int newApptId() throws SQLException {
        ObservableList<appointmentsModel> apptList = appointmentQueries.getAppointmentsList();
        int prevMaxId = 0;
        for(appointmentsModel appt : apptList) {
            if (appt.getApptId() > prevMaxId) {
                prevMaxId = appt.getApptId();
            }
        }
        return ++prevMaxId;
    }

}
