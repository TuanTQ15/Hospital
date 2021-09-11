package com.example.hms.ui.patient.dashboard.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.example.hms.ui.patient.dashboard.medicalrecord.DetailMedicalRecordActivity;
import com.example.hms.ui.patient.dashboard.medicalrecord.MedicalRecordAdapter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalFeeActitvity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HospitalFeeAdapter hospitalFeeAdapter;
    private TextView backId;
    private ProgressBar loadingProgressBar;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    private void getData() {

        userDao= db.userDao();
        API.apiService.getMedicalRecord(userDao.getLogin().getUsername()).enqueue(new Callback<List<MedicalRecordModel>>() {
            @Override
            public void onResponse(Call<List<MedicalRecordModel>> call, Response<List<MedicalRecordModel>> response) {
                if(response.code()==200&&response.body()!=null){
                    setControl(response.body());
                }else  if(response.code()==404){
                    setContentView(R.layout.activity_no_data);
                    backId=findViewById(R.id.backId);
                    backId.setOnClickListener(v -> onBackPressed());
                }
            }

            @Override
            public void onFailure(Call<List<MedicalRecordModel>> call, Throwable t) {
                System.out.println("loi");
            }
        });
    }

    private void setControl(List<MedicalRecordModel> medicalRecordModels) {
        setContentView(R.layout.activity_hospital_fee);
        recyclerView = findViewById(R.id.recycle_view);
        loadingProgressBar = findViewById(R.id.loading);
        backId=findViewById(R.id.backId);
        backId.setOnClickListener(v -> onBackPressed());
        createApdater();
        setMedicalHistoryAdapter(medicalRecordModels);
    }
    private void createApdater(){
        Intent intent = new Intent(this, PaymentActivity.class);
        hospitalFeeAdapter = new HospitalFeeAdapter(medicalRecord -> {
            intent.putExtra("MABA", medicalRecord.getMedicalRecordNumber());
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(hospitalFeeAdapter);
    }
    private void setMedicalHistoryAdapter(List<MedicalRecordModel> medicalRecordModels) {
        if (medicalRecordModels.size() > 0) {
            Collections.sort(medicalRecordModels, (o1, o2) -> o2.getDateLong().compareTo(o1.getDateLong()));
        }
        hospitalFeeAdapter.setMedicalRecordAdapter(medicalRecordModels);
//        tvTotal.setText(String.valueOf(medicalRecordModels.size()));
        loadingProgressBar.setVisibility(View.GONE);
    }

}
