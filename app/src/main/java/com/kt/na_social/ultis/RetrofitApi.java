package com.kt.na_social.ultis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {
    private static final String IP = "192.168.1.32";
    private static final String PORT = "8082";
    private static final String API_BASE_URL = String.format("http://%s:%s/api/", IP, PORT);

    private static Retrofit I;

    public static Retrofit getInstance() {
        if (I == null) {
            I = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return I;
    }
}