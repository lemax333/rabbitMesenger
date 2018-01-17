package com.lemax333.rabbitmessenger.tools.rest;

import com.lemax333.rabbitmessenger.tools.common.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lemax333 on 09.01.18.
 */

public class RestHelper {

    private static Retrofit retrofit;
    private static RabbitMessengerApi rabbitMessengerApi;

    public static RabbitMessengerApi getRabbitMessengerApi() {
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        rabbitMessengerApi = retrofit.create(RabbitMessengerApi.class);
        return rabbitMessengerApi;
    }
}
