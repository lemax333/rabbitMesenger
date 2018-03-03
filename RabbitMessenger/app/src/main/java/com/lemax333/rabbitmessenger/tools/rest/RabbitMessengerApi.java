package com.lemax333.rabbitmessenger.tools.rest;

import com.lemax333.rabbitmessenger.tools.model.ConversationRequest;
import com.lemax333.rabbitmessenger.tools.model.request.GetContactsRequest;
import com.lemax333.rabbitmessenger.tools.model.request.LoginRequest;
import com.lemax333.rabbitmessenger.tools.model.response.GetContactsResponse;
import com.lemax333.rabbitmessenger.tools.model.response.ConversationResponse;
import com.lemax333.rabbitmessenger.tools.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lemax333 on 19.11.17.
 */

public interface RabbitMessengerApi {

    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/register")
    Call<String> register(@Body LoginRequest loginRequest);

    @POST("/api/contacts")
    Call<GetContactsResponse> getContacts(@Body GetContactsRequest getContactsRequest);

    @POST("/api/conversation")
    Call<ConversationResponse> getConversation(@Body ConversationRequest getConversationRequest);

    @POST("/api/conversation/create")
    Call<ConversationResponse> createConversation(@Body ConversationRequest createConversationRequest);
}
