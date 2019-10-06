package com.opus_bd.pilot.RetrofitService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIClientInterface {
    //private static final String BASE_URL = "http://103.106.237.12:85/";
    private static final String BASE_URL = "http://57524caa.ngrok.io/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).

                    addConverterFactory(ScalarsConverterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }

}