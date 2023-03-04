package src.database;

import src.utilities.utilityFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class userQueries {

    public static int loginVerify(String user, String password) throws SQLException {
        int userId = -1;
        String query = "SELECT idUser FROM user WHERE loginName = '"+user+"'AND psw = '"+password+"'";
        ResultSet queryResult = utilityFunctions.DBQuery(query);
        while (queryResult.next()) {
            userId = queryResult.getInt("idUser");
        }
        return Math.max(userId, -1);
    }

    public static int fetchUserId(String UserName) throws SQLException {
        int userId = 0;
        ResultSet userIdQuery = utilityFunctions.DBQuery("SELECT idUser FROM user WHERE loginName = '"+UserName+"'");
        while (userIdQuery.next()) {
            userId = userIdQuery.getInt("idUser");
        }
        return userId;
    }

}
