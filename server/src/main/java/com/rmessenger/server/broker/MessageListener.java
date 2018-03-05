package com.rmessenger.server.broker;

import com.rmessenger.server.broker.model.Message;
import com.rmessenger.server.utils.Constants;
import com.rmessenger.server.utils.JsonMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageListener {

    @Autowired
    MessageSender sender;

    @RabbitListener(queues = Constants.CHAT_APPLICATION_QUEUE)
    public void receiveMessage(byte[] body) throws IOException {
        String jsonMessage = new String(body, "UTF-8");
        if (jsonMessage.equals("Hello") || jsonMessage.equals("hello")) return;
        Message message = JsonMapper.getMessage(jsonMessage);
        //write to database
        sender.sendMessage(message.getConversation(), jsonMessage);
        System.out.println("[x] Message received: " + jsonMessage);
    }

}
