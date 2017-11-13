package com.rmessenger.server.broker.model;

public class Message extends BasicObject{

    private String author;
    private String conversation;
    private String dateTime;
    private String text;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getConversation() {
        return conversation;
    }

    public void setConversation(String conversation) {
        this.conversation = conversation;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "author='" + author + '\'' +
                ", conversation='" + conversation + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
