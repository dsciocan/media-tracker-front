package com.northcoders.media_tracker_front.service;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitInstance {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8080/api/v1/mediatracker/";

    public static UserActionsService getUserService(){
        // Instantiate the custom interceptor and add it to the okhttpclient
        FirebaseUserIdTokenInterceptor interceptor = new FirebaseUserIdTokenInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(UserActionsService.class);
    }






}
