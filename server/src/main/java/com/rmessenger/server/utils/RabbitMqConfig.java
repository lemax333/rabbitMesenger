package com.rmessenger.server.utils;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

    //receiiving messages
    @Bean
    Queue incomingMessegesQueue(){
        return new Queue(Constants.CHAT_APPLICATION_QUEUE, false);
    }

    @Bean
    FanoutExchange outgoingExchange() {
        return new FanoutExchange(Constants.CONVERSATION_OUTGOING_EXHANGE);
    }

    @Bean
    Binding bindingIncomingMessageQueue() {
        return BindingBuilder.bind(incomingMessegesQueue()).to(outgoingExchange());
    }

    //sending messages
    @Bean
    TopicExchange incomingExchange() {
        return new TopicExchange(Constants.CONVERSTATION_INCOMING_EXCHANGE);
    }
}
