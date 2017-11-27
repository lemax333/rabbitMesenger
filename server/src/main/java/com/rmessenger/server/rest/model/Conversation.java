package com.rmessenger.server.rest.model;

public class Conversation {

    private String name;
    private String[] users;

    public Conversation(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }
}
