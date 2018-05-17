package keel.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
public class UserController {

    @ModelAttribute
    public UserForm setup() {
        return new UserForm();
    }

    @GetMapping("validate")
    public String validate(@Validated UserForm userForm) {
        return "success";
    }

    @Data
    class UserForm {

        @NotEmpty
        String userId;
    }
}
