package src.model;

import javafx.collections.ObservableList;
import src.database.appointmentQueries;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Appointments model class
 */
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

    /**
     * 
     * @param apptId Appointment ID, primary key
     * @param apptTitle Appointment Title
     * @param apptDesc Appointment Description
     * @param apptLocation Appointment Location
     * @param apptType Appointment Type
     * @param apptStart Appointment Start Date and Time
     * @param apptEnd Appointment End Date and Time
     * @param apptClientId Client ID
     * @param apptStylist Stylist ID
     */

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

    /**
     *
     * @return appointment ID
     */
    public int getApptId() { return apptId; }

    /**
     *
     * @return appointment title
     */
    public String getApptTitle() { return apptTitle; }

    /**
     *
     * @return appointment description
     */
    public String getApptDesc() { return apptDesc; }

    /**
     *
     * @return appointment location
     */
    public String getApptLocation() {return apptLocation; }

    /**
     *
     * @return stylist ID
     */
    public Integer getApptStylist() {return apptStylist; }

    /**
     *
     * @return appointment type
     */
    public String getApptType() {return apptType; }

    /**
     *
     * @return appointment start time and date
     */
    public LocalDateTime getApptStart() {return apptStart; }

    /**
     *
     * @return appointment end time and date
     */
    public LocalDateTime getApptEnd() {return apptEnd; }

    /**
     *
     * @return client ID
     */
    public int getApptClientId() {return apptClientId; }

    /**
     * Creates a new appointment ID
     * @return previous highest appointment ID +1
     * @throws SQLException
     */
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
