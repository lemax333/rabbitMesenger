package com.lemax333.rabbitmessenger.tools.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oleksii on 21/01/2018.
 */

public class LoginResponse {

    @SerializedName("exchange")
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
