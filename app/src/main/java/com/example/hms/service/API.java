package com.example.hms.service;

import com.example.hms.ModelClass.ChangePassword;
import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.LoginInfoModel;
import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.ModelClass.PaymentModel;
import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.ModelClass.ReceiptModel;
import com.example.hms.ModelClass.StatisticTreament;
import com.example.hms.ModelClass.UserDoctor;
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
import retrofit2.http.Query;

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
    Call<List<EmployeeModel>> getAllEmployees();
    @PUT("api/employees/{maNV}")
    Call<EmployeeModel> udapteEmployee(@Path("maNV") String maNV,@Body EmployeeModel employeeModel);

    @GET("api/employees/login/{maNV}")
    Call<UserDoctor> getLoginDoctor(@Path("maNV") String maNV);

    //payment
    @GET("api/medicalrecords/payment/{CMND}")
    Call<List<MedicalRecordModel>> getMedicalRecord(@Path("CMND") String CMND);

    @GET("api/patients/payment/{MABA}")
    Call<PaymentModel> getHospitalFee(@Path("MABA") String MABA);

    @POST("api/patients/payment")
    Call<ReceiptModel> createReceiptment(@Body ReceiptModel receiptModel);

    @PUT("api/patients/password/change")
    Call<String> changePasssword(@Body ChangePassword changePassword);

    @PUT("api/employees/password/change")
    Call<String> changePassswordEmployee(@Body ChangePassword changePassword);
    @GET("api/departments/statistic/treatment")
    Call<List<StatisticTreament>> getDataSet(@Query("DATEFROM") long dateFrom, @Query("DATETO") long dateTo);

    @GET("api/employees/{maNV}")
    Call <EmployeeModel> getEmployee(@Path("maNV") String maNV);

}

