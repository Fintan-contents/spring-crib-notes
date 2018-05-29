package keel.apierrorhandling.controller;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class UserForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String role;

    @Min(1)
    @Max(150)
    private int age;
}
