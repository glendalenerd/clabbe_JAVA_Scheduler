package src.model;

/**
 * class for the stylist model
 */
public class stylistModel {
    public int stylistId;
    public String stylistName;

    /**
     *
     * @param stylistId stylist ID, primary key
     * @param stylistName stylist name
     */
    public stylistModel(int stylistId, String stylistName) {
        this.stylistId = stylistId;
        this.stylistName= stylistName;


    }

    /**
     *
     * @return stylist ID
     */
    public int getStylistId() {return stylistId;}

    /**
     *
     * @return stylist name
     */
    public String getStylistName() {return stylistName;}
}
