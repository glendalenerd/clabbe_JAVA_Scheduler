package src.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import src.database.clientQueries;

import java.sql.SQLException;

/**
 * class used for the client model
 */

public class clientModel {
    public int clientId;
    public String clientName;
    public String clientEmail;
    public boolean activeStatus;
    public String clientPrefHairColor;
    public String clientStateProv;
    public String clientCountry;
    public String clientZipCode;

    /**
     *
     * @param clientId client ID, primary key
     * @param clientName client name
     * @param clientEmail client email
     * @param activeStatus active status
     * @param clientPrefHairColor client preferred hair color
     * @param clientStateProv client state
     * @param clientCountry client country
     * @param clientZipCode client zip code
     * @throws SQLException
     */
    public clientModel(int clientId, String clientName, String clientEmail, boolean activeStatus, String clientPrefHairColor,
                       String clientStateProv, String clientCountry, String clientZipCode) throws SQLException {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.activeStatus = activeStatus;
        this.clientPrefHairColor = clientPrefHairColor;
        this.clientStateProv = clientStateProv;
        this.clientCountry = clientCountry;
        this.clientZipCode = clientZipCode;
    }

    /**
     *
     * @return client ID
     */
    public int getClientId() {
        return clientId;
    }

    /**
     *
     * @return client name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     *
     * @return client email
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     *
     * @return client active status
     */
    public boolean getActiveStatus() {
        return activeStatus;
    }

    /**
     *
     * @return client preferred hair color
     */
    public String getClientPrefHairColor() {
        return clientPrefHairColor;
    }

    /**
     *
     * @return client state
     */
    public String getClientStateProv() {
        return clientStateProv;
    }

    /**
     *
     * @return client country
     */
    public String getClientCountry() {
        return clientCountry;
    }

    /**
     *
     * @return client zip code
     */
    public String getClientZipCode() {
        return clientZipCode;
    }

    /**
     * creates a new client ID
     * @return previous max client ID +1
     * @throws SQLException
     */
    public static int newClientId() throws SQLException {
        ObservableList<clientModel> clientList = clientQueries.getClientList();
        int prevMaxId = 0;
        for(clientModel c : clientList) {
            if (c.getClientId() > prevMaxId) {
                prevMaxId = c.getClientId();
            }

        }
        return ++prevMaxId;
    }
}
