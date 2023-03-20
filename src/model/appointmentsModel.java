package src.model;

import java.time.LocalDateTime;

public class appointmentsModel {
    public int apptId;
    public String apptTitle;
    public String apptDesc;
    public String apptLocation;
    public String apptStylist;
    public String apptType;
    public LocalDateTime apptStart;
    public LocalDateTime apptEnd;
    public int apptClientId;

    public appointmentsModel(int apptId, String apptTitle, String apptDesc, String apptLocation, String apptStylist,
                             String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptClientId) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.apptDesc = apptDesc;
        this.apptLocation = apptLocation;
        this.apptStylist = apptStylist;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptClientId = apptClientId;

    }
    public int getApptId() { return apptId; }
    public String getApptTitle() { return apptTitle; }
    public String getApptDesc() { return apptDesc; }
    public String getApptLocation() {return apptLocation; }
    public String getApptStylist() {return apptStylist; }
    public String getApptType() {return apptType; }
    public LocalDateTime getApptStart() {return apptStart; }
    public LocalDateTime getApptEnd() {return apptEnd; }
    public int getApptClientId() {return apptClientId; }

}
