package com.northcoders.media_tracker_front.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;
//    private static final String baseURL = "http://wannawatch-env.eba-k3tbfiei.eu-west-2.elasticbeanstalk.com/api/v1/mediatracker/";
    private static final String baseURL = "http://10.0.2.2:8080/api/v1/mediatracker/";
    public static MediaApiService getMovieService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).writeTimeout(2,TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(MediaApiService.class);
    }
    public static UserActionsService getUserService(){
        // Instantiate the custom interceptor and add it to the okhttpclient
        FirebaseUserIdTokenInterceptor interceptor = new FirebaseUserIdTokenInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(2,TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES)
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(UserActionsService.class);
    }
}
