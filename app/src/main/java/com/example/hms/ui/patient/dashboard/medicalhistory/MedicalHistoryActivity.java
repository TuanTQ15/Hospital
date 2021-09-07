package com.example.hms.ui.patient.dashboard.medicalhistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.example.hms.ui.patient.prescription.DetailPrescriptionActivity;
import com.example.hms.ui.patient.prescription.MedicineActivity;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryActivity extends AppCompatActivity {

    private MedicalHistoryAdapter medicalHistoryAdapter;
    private RecyclerView recyclerView;
    private TextView backId;
    private ProgressBar loadingProgressBar;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);
        setControl();
        createApdater();
    }

    private void createApdater(){
        Intent intent = new Intent(this, DetailPrescriptionActivity.class);
        medicalHistoryAdapter = new MedicalHistoryAdapter(medicalHistory -> {
            intent.putExtra("medicalHistory", medicalHistory);
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(medicalHistoryAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setControl() {
        userDao= db.userDao();
        recyclerView=findViewById(R.id.medical_history_recycle_view);
        loadingProgressBar = findViewById(R.id.loading);
        loadingProgressBar.setVisibility(View.VISIBLE);
        backId=findViewById(R.id.backId);
        backId.setOnClickListener(v -> onBackPressed());

        API.apiService.getAllMedicalRecord(userDao.getLogin().getUsername()).enqueue(new Callback<List<MedicalRecordModel>>() {
            @Override
            public void onResponse(Call<List<MedicalRecordModel>> call, Response<List<MedicalRecordModel>> response) {
                if(response.code()==200&&response.body()!=null){
                    List<MedicalRecordModel> medicalRecordModels =response.body();
                    getEmployee(medicalRecordModels);
                }
            }

            @Override
            public void onFailure(Call<List<MedicalRecordModel>> call, Throwable t) {
                System.out.println("loi");
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }
    private void getEmployee(List<MedicalRecordModel> medicalRecordModels){
        API.apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if(response.body()!=null&&response.code()==200){
                    List<EmployeeModel> employeeModels =response.body();
                    setMedicalHistoryAdapter(medicalRecordModels,employeeModels);
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                System.out.println("loi");
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setMedicalHistoryAdapter(List<MedicalRecordModel> medicalRecordModels,
                                          List<EmployeeModel> employeeModels) {
        if (medicalRecordModels.size() > 0) {
            Collections.sort(medicalRecordModels, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        }
        medicalHistoryAdapter.setMedicalHistoryAdapter(medicalRecordModels.get(0).getMedicalHistorys(),employeeModels);
        loadingProgressBar.setVisibility(View.GONE);
    }

}
