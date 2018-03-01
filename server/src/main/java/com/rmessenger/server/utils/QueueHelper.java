package com.rmessenger.server.utils;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueHelper {

    @Autowired
    AmqpAdmin admin;
    @Autowired
    TopicExchange incomingExchange;

    public FanoutExchange createExchange(String exchangeName){
        FanoutExchange exchange =  new FanoutExchange(exchangeName);
        admin.declareExchange(exchange);
        return exchange;
    }

    public void bindExchangeByConversationId(String exchange, String conversationId){
        Binding binding = BindingBuilder.bind(new FanoutExchange(exchange.replaceAll("\\s+",""))).to(incomingExchange).with(conversationId);
        admin.declareBinding(binding);
    }
}
