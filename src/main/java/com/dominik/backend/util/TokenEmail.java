package com.dominik.backend.util;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by dominik on 06.06.2017.
 */
public class TokenEmail {

    @NotNull(message = "{null.message}")
    @org.hibernate.validator.constraints.Email(message = "{email.message}")
    private String email;

    protected TokenEmail() {}

    public TokenEmail(String email) {
        this.email = email;
    }

    @JsonProperty("EMAIL")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
