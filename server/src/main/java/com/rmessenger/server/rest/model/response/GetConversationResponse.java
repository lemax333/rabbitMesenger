package com.rmessenger.server.rest.model.response;

public class GetConversationResponse {

    private String conversationName;

    public GetConversationResponse(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }
}
