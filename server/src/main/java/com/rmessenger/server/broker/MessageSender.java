package com.rmessenger.server.broker;

import com.rmessenger.server.utils.Constants;
import com.rmessenger.server.utils.DbHelper;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    AmqpTemplate template;
    @Autowired
    DbHelper dbHelper;

    public void sendMessage(String conversation, String jsonMessage) {
        //dbHelper.get
        template.convertAndSend(Constants.CONVERSTATION_INCOMING_EXCHANGE, conversation, jsonMessage);
    }
}
