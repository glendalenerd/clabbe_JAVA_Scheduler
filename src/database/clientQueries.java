package src.database;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.model.clientModel;
import src.utilities.utilityFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class clientQueries {
    public static ObservableList<clientModel> getClientList() throws SQLException {
        ObservableList<clientModel> clientListAll = FXCollections.observableArrayList();
        String clientsQuery = "SELECT * FROM client";
        ResultSet clientsFetch = utilityFunctions.DBQuery(clientsQuery);
        while (clientsFetch.next()) {
            int clientId = clientsFetch.getInt("idclient");
            String clientName = clientsFetch.getString("name");
            String clientEmail = clientsFetch.getString("email");
            int activeStatus = clientsFetch.getInt("Active");
            String clientPrefHair = clientsFetch.getString("haircolor");
            String clientStateProv = clientsFetch.getString("st_pv");
            String clientCountry = clientsFetch.getString("country");
            String clientPostalCode = clientsFetch.getString("pcode");
            clientModel client = new clientModel(clientId, clientName, clientEmail, activeStatus, clientPrefHair,
                    clientStateProv, clientCountry, clientPostalCode);
            clientListAll.add(client);
        }
        return clientListAll;
    }
}
