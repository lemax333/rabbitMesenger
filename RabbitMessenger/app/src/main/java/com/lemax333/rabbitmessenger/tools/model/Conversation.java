package com.lemax333.rabbitmessenger.tools.model;

/**
 * Created by lemax333 on 15.12.17.
 */

public class Conversation {
    String name;
    String lastMessage;

    public Conversation(String name, String lastMessage) {
        this.name = name;
        this.lastMessage = lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
