package src.model;

public class userModel {
    public String userName;
    public String userPassword;
    public int userId;

    public userModel(String userName, String userPassword, int userId) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userId = userId;
    }
    public String fetchUserName() {
        return userName;
    }
    public String fetchUserPassword() {
        return userPassword;
    }
    public int fetchUserId() {
        return userId;
    }
}
