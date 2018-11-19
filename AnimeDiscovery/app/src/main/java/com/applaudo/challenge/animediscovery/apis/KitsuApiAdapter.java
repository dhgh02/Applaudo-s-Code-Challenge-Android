package com.applaudo.challenge.animediscovery.apis;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KitsuApiAdapter {

    private static KitsuApiService API_SERVICE;

    public static KitsuApiService getApiService(){
        //Create interceptor and setting intercepter to the log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        //Adding logging as last interceptor to register all the call
        httpClient.addInterceptor(logging);

        //Setting base URL
        String baseUrl = "https://kitsu.io/api/edge/";

        if (API_SERVICE == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()) //Using GSON Converter
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //Using RxJava for executing multiple calls at once
                    .client(httpClient.build())
                    .build();
            API_SERVICE = retrofit.create(KitsuApiService.class);
        }

        return API_SERVICE;
    }
}
