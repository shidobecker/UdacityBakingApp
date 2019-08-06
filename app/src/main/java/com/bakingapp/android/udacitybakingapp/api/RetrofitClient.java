package com.bakingapp.android.udacitybakingapp.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private final Retrofit retrofit;

    private RetrofitClient(){

        OkHttpClient client = buildClient();

        retrofit = new Retrofit.Builder().baseUrl(RecipesApi.RECIPES_BASE_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private OkHttpClient buildClient()  {

        OkHttpClient.Builder builder =
                new OkHttpClient.Builder();

        return builder.build();
    }


    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }

        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


}
