package keel.controller.form;

import jakarta.validation.constraints.NotEmpty;

public class UserUpdateForm {
    private long userId;
    @NotEmpty
    private String userName;
    private long versionNo;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(long versionNo) {
        this.versionNo = versionNo;
    }
}
