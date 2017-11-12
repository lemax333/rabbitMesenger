package com.rmessenger.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    Sender sender;

    public void receiveMessage(String message) {
        System.out.println("[x] Message received: " + message);
        sender.sendMessageToExchange(message);
    }

}
