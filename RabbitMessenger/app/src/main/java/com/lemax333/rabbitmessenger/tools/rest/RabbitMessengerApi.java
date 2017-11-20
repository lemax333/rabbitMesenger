package com.lemax333.rabbitmessenger.tools.rest;

import com.lemax333.rabbitmessenger.tools.model.UserAuthentication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lemax333 on 19.11.17.
 */

public interface RabbitMessengerApi {

    @POST("/api/login")
    Call<String> login(@Body UserAuthentication userAuthentication);
}