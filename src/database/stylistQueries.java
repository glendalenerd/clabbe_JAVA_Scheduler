package src.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.model.clientModel;
import src.model.stylistModel;
import src.utilities.utilityFunctions;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

public class stylistQueries {
    public static String getStylistName(int stylistId) throws SQLException {
        String stylistName = "";
        String stylistQueryText = "SELECT name FROM stylist WHERE idstylist = ";
        ResultSet stylistNameQuery = utilityFunctions.DBQuery(stylistQueryText+stylistId);
        while (stylistNameQuery.next()) {
            stylistName = stylistNameQuery.getString("name");
        }
        return stylistName;
    }
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
