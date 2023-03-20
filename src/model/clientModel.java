package src.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import src.database.clientQueries;

import java.sql.SQLException;

public class clientModel {
    public int clientId;
    public String clientName;
    public String clientEmail;
    public boolean activeStatus;
    public String clientPrefHairColor;
    public String clientStateProv;
    public String clientCountry;
    public String clientZipCode;

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

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public boolean getActiveStatus() {
        return activeStatus;
    }

    public String getClientPrefHairColor() {
        return clientPrefHairColor;
    }

    public String getClientStateProv() {
        return clientStateProv;
    }

    public String getClientCountry() {
        return clientCountry;
    }

    public String getClientZipCode() {
        return clientZipCode;
    }

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
