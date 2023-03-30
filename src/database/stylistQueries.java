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
