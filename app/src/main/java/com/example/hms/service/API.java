package com.example.hms.service;

import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.LoginInfoModel;
import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.ModelClass.PaymentModel;
import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.ModelClass.UserPatient;
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
    @GET("api/patients/login/{CMND}")
    Call<UserPatient> getLoginPatient(@Path("CMND") String CMND);

    @PUT("api/patients/{CMND}")
    Call<PatientModel> updatePatient(@Path("CMND") String CMND, @Body PatientModel patient);


    @GET("api/prescriptions")
    Call<List<PrescriptionModel>> getAllPrescription();

    @GET("api/medicalrecords/{CMND}")
    Call<List<MedicalRecordModel>> getAllMedicalRecord(@Path("CMND") String CMND);

    @GET("api/employees")
    Call <List<EmployeeModel>> getAllEmployees();

    @GET("api/patients/payment/{CMND}")
    Call <PaymentModel> getHospitalFee(@Path("CMND") String CMND);
}
