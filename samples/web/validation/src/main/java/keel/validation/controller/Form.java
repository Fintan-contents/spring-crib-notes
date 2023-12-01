package keel.validation.controller;

import keel.validation.value.MailAddress;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Form {

    // type-converter-error-start
    @NotEmpty
    @Length(max = 255)
    private String name;

    private MailAddress mailAddress;

    @NotNull
    private Integer age;

    @NotEmpty
    private String role;
    // type-converter-error-end

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public MailAddress getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(final MailAddress mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }
}
