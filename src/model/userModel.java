package src.model;

/**
 * class used for the user model
 */
public class userModel {
    public String userName;
    public String userPassword;
    public int userId;

    /**
     *
     * @param userName username, primary key
     * @param userPassword user password
     * @param userId user ID
     */
    public userModel(String userName, String userPassword, int userId) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userId = userId;
    }

    /**
     *
     * @return username
     */
    public String fetchUserName() {
        return userName;
    }

    /**
     *
     * @return user password
     */
    public String fetchUserPassword() {
        return userPassword;
    }

    /**
     *
     * @return user ID
     */
    public int fetchUserId() {
        return userId;
    }
}
