package src.database;

import src.utilities.utilityFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used for methods related to users, including login verification and fetching user names
 */
public class userQueries {

    /**
     * Verifies user name and password
     * @param user that will be verified
     * @param password that will be verified with the user
     * @return Math Max that gives the next sequential number to be used for the user ID
     * @throws SQLException
     */
    public static int loginVerify(String user, String password) throws SQLException {
        int userId = -1;
        String query = "SELECT idUser FROM user WHERE loginName = '"+user+"'AND psw = '"+password+"'";
        ResultSet queryResult = utilityFunctions.DBQuery(query);
        while (queryResult.next()) {
            userId = queryResult.getInt("idUser");
        }
        return Math.max(userId, -1);
    }

    /**
     * Retrieves the user ID
     * @param UserName that will be used in the query to fetch the User ID
     * @return userId
     * @throws SQLException
     */
    public static int fetchUserId(String UserName) throws SQLException {
        int userId = 0;
        ResultSet userIdQuery = utilityFunctions.DBQuery("SELECT idUser FROM user WHERE loginName = '"+UserName+"'");
        while (userIdQuery.next()) {
            userId = userIdQuery.getInt("idUser");
        }
        return userId;
    }

}
