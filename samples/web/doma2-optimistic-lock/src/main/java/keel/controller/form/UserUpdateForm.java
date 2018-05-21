package keel.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateForm {
    private long userId;
    @NotEmpty
    private String userName;
    private long versionNo;
}
