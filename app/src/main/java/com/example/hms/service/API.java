package com.example.hms.service;

import com.example.hms.ModelClass.LoginInfoModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.ModelClass.PrescriptionModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    Gson gson = new GsonBuilder().create();

    API apiService = new Retrofit.Builder()
            .baseUrl("https://ptithcm-hospital-manager.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(API.class);


    @POST("api/login")
    Call<LoginInfoModel> login(@Body RequestBody requestBody);
    @GET("api/patients/{CMND}")
    Call<PatientModel> getPatient(@Path("CMND") String CMND);
    @PUT("api/patients/{CMND}")
    Call<PatientModel> updatePatient(@Path("CMND") String CMND, @Body PatientModel patient);


    @GET("api/prescriptions")
    Call<List<PrescriptionModel>> getAllPrescription();
}
