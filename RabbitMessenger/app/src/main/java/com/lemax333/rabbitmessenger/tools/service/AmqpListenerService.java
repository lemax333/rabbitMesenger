package com.lemax333.rabbitmessenger.tools.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lemax333.rabbitmessenger.tools.common.Constants;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by oleksii on 03/03/2018.
 */

public class AmqpListenerService extends IntentService {

    public static final String USERANAME = "username";
    public static final String EXCHANGE = "exchange";
    public static final String MESSAGE = "message";

    private ConnectionFactory connectionFactory;
    private String username;
    private String exchange;
    private String queueName;
    private boolean isRunning = true;
    ResultReceiver receiver;

    private Thread subscribeThread;

    public AmqpListenerService() {
        super("AmqpListenerService");
    }

    public AmqpListenerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        username = intent.getStringExtra(USERANAME);
        exchange = intent.getStringExtra(EXCHANGE);
        queueName = username + ".messages";
        receiver = intent.getParcelableExtra(com.lemax333.rabbitmessenger.utils.Constants.DATA_RECEIVER);
        declareListener();
    }

    private void declareListener() {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectionFactory = new ConnectionFactory();
                    connectionFactory.setHost(Constants.AMQP_HOST);
                    connectionFactory.setUsername(Constants.AMQP_USER);
                    connectionFactory.setPassword(Constants.AMQP_PASSWORE);
                    Connection connection = connectionFactory.newConnection();
                    Channel channel = connection.createChannel();
                    channel.confirmSelect();
                    AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queueName, false, false, false, null);
                    channel.queueBind(queueName, exchange, "");
                    QueueingConsumer consumer = new QueueingConsumer(channel);
                    channel.basicConsume(declareOk.getQueue(), true, consumer);
                    while (true) {
                        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                        String message = new String(delivery.getBody());
                        Bundle bundle = new Bundle();
                        bundle.putString(MESSAGE, message);
                        receiver.send(com.lemax333.rabbitmessenger.utils.Constants.STATUS_OK, bundle);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        subscribeThread.start();
        while(isRunning){
            Log.d("AmqpListenerService", "waiting for messages");
        }
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        subscribeThread.interrupt();
        super.onDestroy();
    }
}
