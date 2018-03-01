package com.rmessenger.server.rest.model.response;

public class CreateConversationResponse {

    private String conversationName;

    public CreateConversationResponse() {
    }

    public CreateConversationResponse(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    @Override
    public String toString() {
        return "CreateConversationResponse{" +
                "conversationName='" + conversationName + '\'' +
                '}';
    }
}
