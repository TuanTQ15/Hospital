package com.example.hms.service;

import com.example.hms.ModelClass.LoginInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {
    Gson gson = new GsonBuilder().create();

    API apiService = new Retrofit.Builder()
            .baseUrl("https://ptithcm-hospital-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(API.class);


    @POST("api/login")
    Call<LoginInfo> login(@Body RequestBody requestBody);

}
