package com.opus_bd.pilot.RetrofitService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    /* private static final String BASE_URL = "http://1a2c4eb8.ngrok.io/SalesTrack/public/";*/
    //private static final String BASE_URL = "http://103.106.237.12:90/global/";
    private static final String BASE_URL = "http://dab4650f.ngrok.io/global/";
    private static Retrofit retrofit;
    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
