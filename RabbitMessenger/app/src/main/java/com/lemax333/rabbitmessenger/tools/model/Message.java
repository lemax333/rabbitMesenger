package com.lemax333.rabbitmessenger.tools.model;

/**
 * Created by oleksii on 03/03/2018.
 */

public class Message {

    private String author;
    private String text;
    private String conversation;

    public Message() {
    }

    public Message(String author, String text, String conversation) {
        this.author = author;
        this.text = text;
        this.conversation = conversation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }
}
