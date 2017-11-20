package com.rmessenger.server.rest.model;

import com.fasterxml.jackson.annotation.JacksonInject;

public class UserAuthentication {

    private String login;
    private String password;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
