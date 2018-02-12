package com.lemax333.rabbitmessenger.tools.model.request;

/**
 * Created by oleksii on 12/02/2018.
 */

public class GetContactsRequest {

    private String username;

    public GetContactsRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "GetContactsRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
