package com.rmessenger.server.broker;

import com.rmessenger.server.utils.Constants;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    AmqpTemplate template;

    public void sendMessage(String conversation, String jsonMessage) {
        template.convertAndSend(Constants.CONVERSTATION_INCOMING_EXCHANGE, conversation, jsonMessage);
    }
}
