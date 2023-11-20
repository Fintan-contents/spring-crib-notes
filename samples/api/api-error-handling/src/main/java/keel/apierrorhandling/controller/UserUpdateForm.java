package keel.apierrorhandling.controller;

import jakarta.validation.constraints.NotNull;

public class UserUpdateForm extends UserForm {

    @NotNull
    private Long versionNo;

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
}
