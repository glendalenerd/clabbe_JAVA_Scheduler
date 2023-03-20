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
            boolean activeStatus = clientsFetch.getBoolean("Active");
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
    public static void addClient(clientModel client) throws SQLException {
        Integer clientId = client.getClientId();
        String clientName = client.getClientName();
        String clientEmail = client.getClientEmail();
        String clientHairColor = client.getClientPrefHairColor();
        String clientPostalCode = client.getClientZipCode();
        String clientStaveProv = client.getClientStateProv();
        String clientCountry = client.getClientCountry();
        boolean clientActiveStatus = client.getActiveStatus();
        String queryCommand = "INSERT INTO client VALUES ";
        String queryText = clientId+", '"+clientName+"', '"+clientEmail+"', '"+clientHairColor+"', '"+clientPostalCode+
                "', '"+clientStaveProv+"', '"+clientCountry+"', "+clientActiveStatus;
        System.out.println(queryCommand+"("+queryText+");");
        utilityFunctions.DBExec(queryCommand+"("+queryText+");");
    }
}
