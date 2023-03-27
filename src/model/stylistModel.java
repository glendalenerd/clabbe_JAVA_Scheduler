package src.model;

public class stylistModel {
    public int stylistId;
    public String stylistName;
    public stylistModel(int stylistId, String stylistName) {
        this.stylistId = stylistId;
        this.stylistName= stylistName;


    }

    public int getStylistId() {return stylistId;}
    public String getStylistName() {return stylistName;}
}
