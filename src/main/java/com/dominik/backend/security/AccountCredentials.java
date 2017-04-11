package com.dominik.backend.security;

/**
 * Created by dominik on 11.04.2017.
 */

public class AccountCredentials {

    private String login;
    private String password;

    public AccountCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
}
