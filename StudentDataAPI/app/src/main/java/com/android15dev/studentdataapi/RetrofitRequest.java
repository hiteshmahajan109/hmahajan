package com.android15dev.studentdataapi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    public static APIInterfaces getInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl("https://hiteshmahajan.000webhostapp.com/api/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build().create(APIInterfaces.class);
    }
}
