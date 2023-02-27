package src.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "altdb";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement;

    /**
     * Establishing a connection to database with provided credentials
     */
    public static void makeConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * @return Database connection handle
     */
    public static Connection getConnection() { return connection;}

    /**
     * Closing the database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param sqlStatement Query to be pushed into statement
     * @param conn Handle to connection
     * @throws SQLException Handles SQL query exception
     */
    public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
        if (conn != null) {
            preparedStatement = conn.prepareStatement(sqlStatement);
        }
        else {
            System.out.println("Prepared Statement Creation Failed");
        }
    }

    /**
     * @return statement that is stored as prepared
     */
    public static PreparedStatement getPreparedStatement() {
        if (preparedStatement != null) {
            return preparedStatement;
        }
        else {
            System.out.println("Null reference to prepared statement");
        }
        return null;
    }
}