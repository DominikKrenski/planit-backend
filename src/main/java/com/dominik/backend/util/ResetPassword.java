package com.dominik.backend.util;

import com.dominik.backend.validator.PasswordMatch;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by dominik on 06.06.2017.
 */

@PasswordMatch(first = "password", second = "passwordConfirm")
public class ResetPassword {

    @NotNull(message = "{null.message}")
    private Long id;

    @NotNull(message = "{null.message}")
    private String token;

    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    private String password;

    @NotNull(message = "{null.message}")
    @Size(min = 5, message = "{passwordLength.message}")
    private String passwordConfirm;

    protected ResetPassword() {}

    public ResetPassword(Long id, String token, String password, String passwordConfirm) {
        this.id = id;
        this.token = token;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    @JsonProperty("ID")
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty("TOKEN")
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @JsonProperty("PASSWORD")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty("PASSWORD_CONFIRM")
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
