package com.lemax333.rabbitmessenger.tools.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.lemax333.rabbitmessenger.tools.model.ConversationRequest;
import com.lemax333.rabbitmessenger.tools.model.request.GetContactsRequest;
import com.lemax333.rabbitmessenger.tools.model.response.ConversationResponse;
import com.lemax333.rabbitmessenger.tools.rest.RabbitMessengerApi;
import com.lemax333.rabbitmessenger.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oleksii on 03/03/2018.
 */

public class ConversationService extends IntentService {

    private static final String CONVERSATION_NAME = "conversationName";
    public static final String FIRST_USER = "firstUser";
    public static final String SECONDE_USER = "secondUser";
    Retrofit retrofit;
    RabbitMessengerApi rabbitMessengerApi;

    public ConversationService(){
        super("ContactsService");
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.2:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        rabbitMessengerApi = retrofit.create(RabbitMessengerApi.class);
    }
    public ConversationService(String name) {
        super(name);
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.2:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        rabbitMessengerApi = retrofit.create(RabbitMessengerApi.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String firsUser = intent.getStringExtra(FIRST_USER);
        String secondUser = intent.getStringExtra(SECONDE_USER);
        ResultReceiver receiver = intent.getParcelableExtra(Constants.DATA_RECEIVER);
        final Bundle data = new Bundle();
        String conversationName = "";
        try {
            Response<ConversationResponse> conversation = rabbitMessengerApi.getConversation(new ConversationRequest(firsUser, secondUser)).execute();
            conversationName = conversation.body().getConversationName();
            if (conversationName.isEmpty()){
                Response<ConversationResponse> newConversation = rabbitMessengerApi.createConversation(new ConversationRequest(firsUser, secondUser)).execute();
                conversationName = newConversation.body().getConversationName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.putString(CONVERSATION_NAME, conversationName);
        receiver.send(Constants.STATUS_OK, data);
    }
}
