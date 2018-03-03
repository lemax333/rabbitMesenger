package com.lemax333.rabbitmessenger.tools.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.lemax333.rabbitmessenger.tools.common.Constants;
import com.lemax333.rabbitmessenger.tools.model.Message;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by oleksii on 03/03/2018.
 */

public class AmqpSender extends IntentService {

    public static String MESSAGE = "message";
    private ConnectionFactory connectionFactory;

    public AmqpSender(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }

    private void sendMessage(Message message){
        try {
            Gson gson = new Gson();
            String jsonMessage = gson.toJson(message);
            Connection connection = this.connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.confirmSelect();
            channel.basicPublish(Constants.EXCHANGE_FOR_SENDING, "", null, jsonMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
