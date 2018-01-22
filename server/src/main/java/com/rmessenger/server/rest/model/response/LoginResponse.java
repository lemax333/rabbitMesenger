package com.rmessenger.server.rest.model.response;

public class LoginResponse {

    private String exchange;

    public LoginResponse(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
