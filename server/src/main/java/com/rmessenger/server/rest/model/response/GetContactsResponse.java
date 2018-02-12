package com.rmessenger.server.rest.model.response;

import java.util.List;

public class GetContactsResponse {

    private List<String> contacts;

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
}
