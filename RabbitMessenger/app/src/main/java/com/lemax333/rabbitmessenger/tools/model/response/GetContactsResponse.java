package com.lemax333.rabbitmessenger.tools.model.response;

import java.util.List;

/**
 * Created by oleksii on 12/02/2018.
 */

public class GetContactsResponse {

    private List<String> contacts;

    public GetContactsResponse(List<String> contacts) {
        this.contacts = contacts;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "GetContactsResponse{" +
                "contacts=" + contacts +
                '}';
    }
}
