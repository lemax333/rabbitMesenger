package com.lemax333.rabbitmessenger.tools.model;

/**
 * Created by oleksii on 03/03/2018.
 */

public class ConversationRequest {

    private String firstUser;
    private String secondUser;

    public ConversationRequest(String firstUser, String secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }
}
