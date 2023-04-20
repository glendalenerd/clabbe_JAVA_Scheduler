package src.database;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import src.model.clientModel;
import src.utilities.utilityFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for methods related to clients including fetching the list of clients, adding and editing clients
 */
public class clientQueries {

    /**
     * Retrieving the list of clients that are part of the client table
     * @return clientListAll
     * @throws SQLException
     */
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

    /**
     * Uses the client model to construct the INSERT command and uses the DBEXEC method to add the new client to the client
     * table
     * @param client
     * @throws SQLException
     */
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
        //System.out.println(queryCommand+"("+queryText+");");
        utilityFunctions.DBExec(queryCommand+"("+queryText+");");
    }

    /**
     * Uses client model to construct the UPDATE command and uses the DBEXEC method to edit the client in the
     * client table
     * @param client
     * @throws SQLException
     */
    public static void editClient(clientModel client) throws SQLException {
        String queryCommand = "UPDATE client SET ";
        String queryText = "name = '" + client.getClientName() + "', email = '" + client.getClientEmail() + "', haircolor = '" +
                client.getClientPrefHairColor() + "', pcode = '" + client.getClientZipCode() + "', st_pv = '" +
                client.getClientStateProv() + "', country = '" + client.getClientCountry() + "', Active = " + client.getActiveStatus() +
                " WHERE idclient = " + client.getClientId();
        //System.out.println("edit: " + queryCommand+queryText);
        utilityFunctions.DBExec(
            queryCommand+queryText
        );
    }

    /**
     * Constructs the SELECT command and uses the DBQuery method to retrieve a list of the client's appointments
     * @param clientId
     * @return clientApptList
     * @throws SQLException
     */
    public static List<Integer> getClientAppts(int clientId) throws SQLException {
        List<Integer> clientApptList = new ArrayList<>();
        String clientApptQuery = "SELECT idappt FROM appt WHERE clientid = " + clientId;
        ResultSet clientApptResults = utilityFunctions.DBQuery(clientApptQuery);
        while (clientApptResults.next()) {
            clientApptList.add(clientApptResults.getInt("idappt"));
        }
        return clientApptList;
    }

    /**
     * Constructs the DELETE command and uses the DBExec method to delete the client from the client table
     * @param clientId
     * @throws SQLException
     */
    public static void deleteClient(int clientId) throws SQLException {
        //List<Integer> clientAppts = getClientAppts(clientId);
        String clientDeleteQuery = "DELETE FROM client WHERE idclient = " + clientId;
        System.out.println("delete: "+clientDeleteQuery);
        utilityFunctions.DBExec(clientDeleteQuery);
    }

}
