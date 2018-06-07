package keel.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
public class UserController {

    @GetMapping("validate")
    public String validate(@Validated UserForm userForm) {
        return "success";
    }

    @Data
    static class UserForm {

        @NotEmpty
        String userId;
    }
}
