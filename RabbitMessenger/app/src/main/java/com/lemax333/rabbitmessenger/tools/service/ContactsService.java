package com.lemax333.rabbitmessenger.tools.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.lemax333.rabbitmessenger.tools.model.request.GetContactsRequest;
import com.lemax333.rabbitmessenger.tools.model.response.GetContactsResponse;
import com.lemax333.rabbitmessenger.tools.rest.RabbitMessengerApi;
import com.lemax333.rabbitmessenger.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oleksii on 12/02/2018.
 */

public class ContactsService extends IntentService {

    private static final String CONTACTS_LIST = "contacts_list";
    private final String USERNAME = "username";
    Retrofit retrofit;
    RabbitMessengerApi rabbitMessengerApi;

    public ContactsService(){
        super("ContactsService");
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.2:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        rabbitMessengerApi = retrofit.create(RabbitMessengerApi.class);
    }
    public ContactsService(String name) {
        super(name);
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.2:8080")
                .addConverterFactory(GsonConverterFactory.create()).build();
        rabbitMessengerApi = retrofit.create(RabbitMessengerApi.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String userName = intent.getStringExtra(Constants.USERNAME);
        ResultReceiver receiver = intent.getParcelableExtra(Constants.DATA_RECEIVER);
        final Bundle data = new Bundle();
        ArrayList<String> contactsList = new ArrayList<>();
        try {
            Response<GetContactsResponse> contacts = rabbitMessengerApi.getContacts(new GetContactsRequest(userName)).execute();
            contactsList.addAll(contacts.body().getContacts());
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.putStringArrayList(CONTACTS_LIST, contactsList);
        receiver.send(Constants.STATUS_OK, data);
    }
}