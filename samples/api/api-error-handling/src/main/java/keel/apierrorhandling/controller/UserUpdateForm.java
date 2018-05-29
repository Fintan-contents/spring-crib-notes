package keel.apierrorhandling.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateForm extends UserForm {

    @NotNull
    private Long versionNo;
}
