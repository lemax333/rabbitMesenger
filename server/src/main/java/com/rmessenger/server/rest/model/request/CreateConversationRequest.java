package com.rmessenger.server.rest.model.request;

public class CreateConversationRequest {

    private String firstUser;
    private String secondUser;

    public CreateConversationRequest(){}

    public CreateConversationRequest(String firstUser, String secondUser) {
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

    @Override
    public String toString() {
        return "CreateConversationRequest{" +
                "firstUser='" + firstUser + '\'' +
                ", secondUser='" + secondUser + '\'' +
                '}';
    }
}
