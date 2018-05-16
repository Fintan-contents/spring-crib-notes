package jp.co.tis.keel;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Null;

public class User {

    @Length(max = 2)
    @Null
    private String userId;
    private String userName;

    public User() {
    }

    public User(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "userName: " + userName + ", userId: " + userId;
    }

}
