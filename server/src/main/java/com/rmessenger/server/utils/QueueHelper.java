package com.rmessenger.server.utils;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueHelper {

    @Autowired
    AmqpAdmin admin;

    public FanoutExchange createExchange(String exchangeName){
        FanoutExchange exchange =  new FanoutExchange(exchangeName);
        admin.declareExchange(exchange);
        return exchange;
    }
}
