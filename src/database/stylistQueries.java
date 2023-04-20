package src.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.model.clientModel;
import src.model.stylistModel;
import src.utilities.utilityFunctions;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class used for methods related to stylists, including getting a stylist's name and retrieving the list of stylists
 */
public class stylistQueries {
    /**
     * Retrieves the stylist's name
     * @param stylistId
     * @return stylistName
     * @throws SQLException
     */
    public static String getStylistName(int stylistId) throws SQLException {
        String stylistName = "";
        String stylistQueryText = "SELECT name FROM stylist WHERE idstylist = ";
        ResultSet stylistNameQuery = utilityFunctions.DBQuery(stylistQueryText+stylistId);
        while (stylistNameQuery.next()) {
            stylistName = stylistNameQuery.getString("name");
        }
        return stylistName;
    }

    /**
     * Retrieves the complete list of stylists from the stylist table
     * @return StylistListAll
     * @throws SQLException
     */
    public static ObservableList<stylistModel> getStylistList() throws SQLException {
        ObservableList<stylistModel> stylistListAll = FXCollections.observableArrayList();
        String stylistQuery = "SELECT * FROM stylist";
        ResultSet stylistFetch = utilityFunctions.DBQuery(stylistQuery);
        while (stylistFetch.next()) {
            int stylistId = stylistFetch.getInt("idstylist");
            String stylistName = stylistFetch.getString("name");
            stylistModel stylist = new stylistModel(stylistId, stylistName);
            stylistListAll.add(stylist);
        }
        return stylistListAll;
    }
    //public static ObservableList<stylistModel> getS
}
