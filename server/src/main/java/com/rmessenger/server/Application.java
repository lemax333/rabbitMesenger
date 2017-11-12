package com.rmessenger.server;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    final static String QUEUE_NAME = "chat-application-messages";
    final static String EXCHANGE_NAME = "conversation.outgoing";

    final static String SENDER_EXCHANGE_NAME = "converstaion.incoming";

    final static String CLIENT1_EXCHANGE = "client1.incoming";
    final static String CLIENT2_EXCHANGE = "client2.incoming";

    @Autowired
    Receiver receiver;
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    Queue client1Queue(){
        return  new Queue(CLIENT1_EXCHANGE, false);
    }

    @Bean
    Queue client2Queue(){
        return  new Queue(CLIENT2_EXCHANGE, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(SENDER_EXCHANGE_NAME, false, true);
    }

    @Bean
    Binding client1Binding() {
        return BindingBuilder.bind(client1Queue()).to(topicExchange()).with(CLIENT1_EXCHANGE);
    }

    @Bean
    Binding client2Binding() {
        return BindingBuilder.bind(client2Queue()).to(topicExchange()).with(CLIENT2_EXCHANGE);
    }

    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange());
    }

    /*@Bean
    SimpleMessageListenerContainer getContainer(ConnectionFactory connectionFactory,
                                                MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }*/

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer1(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                receiver.receiveMessage(new String(message.getBody()));
            }
        });
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
