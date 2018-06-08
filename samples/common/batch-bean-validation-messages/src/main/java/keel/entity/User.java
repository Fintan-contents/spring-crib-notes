package keel.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class User {

    @NotEmpty
    @Length(min = 8, max = 20)
    private String userId;

    @NotEmpty
    private String userName;

    public String getUserId() {
        return userId;
    }

    public User() {
    }

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
