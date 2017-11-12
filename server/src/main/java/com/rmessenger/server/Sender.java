package com.rmessenger.server;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessageToExchange(String message){
        String receiver = message.split(",")[0];
        String text = message.split(",")[1];
        String clientExchange = receiver == "Client1" ? Application.CLIENT1_EXCHANGE : Application.CLIENT2_EXCHANGE;
        this.rabbitTemplate.convertAndSend(Application.SENDER_EXCHANGE_NAME, clientExchange, text);
    }


}
