package com.lemax333.rabbitmessenger.tools.model;

/**
 * Created by lemax333 on 19.11.17.
 */

public class UserAuthenticationRequest {

    private String login;
    private String password;

    public UserAuthenticationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

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
