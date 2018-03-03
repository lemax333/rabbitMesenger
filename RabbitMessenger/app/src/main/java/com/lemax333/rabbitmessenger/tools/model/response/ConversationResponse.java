package com.lemax333.rabbitmessenger.tools.model.response;

/**
 * Created by oleksii on 03/03/2018.
 */

public class ConversationResponse {

    private String conversationName;

    public ConversationResponse(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }
}
