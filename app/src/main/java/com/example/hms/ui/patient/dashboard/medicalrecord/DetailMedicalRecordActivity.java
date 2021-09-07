package com.example.hms.ui.patient.dashboard.medicalrecord;

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
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.example.hms.ui.patient.dashboard.medicalhistory.MedicalHistoryAdapter;
import com.example.hms.ui.patient.prescription.DetailPrescriptionActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMedicalRecordActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView backId;
    private ProgressBar loadingProgressBar;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    private TextView tvFullName,tvAddress,tvPhoneNumber,tvBHYT,tvCMND,
            tvGender, tvBirthday,tvWeight,tvHeight ,tvPastMedicalRecord,tvHistoryRecordNumber;
    private MedicalRecordModel medicalRecord;
    private MedicalHistoryAdapter medicalHistoryAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_medical_record);
        setControl();
        createApdater();
        getPatient();

    }

    private void setControl() {
        recyclerView=findViewById(R.id.medical_history_recycle_view);
        loadingProgressBar = findViewById(R.id.loading);
        tvHistoryRecordNumber = findViewById(R.id.record_number);
        tvFullName = findViewById(R.id.full_name);
        tvAddress = findViewById(R.id.address);
        tvPhoneNumber = findViewById(R.id.phoneNumber);
        tvBHYT = findViewById(R.id.bhyt);
        tvCMND = findViewById(R.id.cmnd);
        tvGender = findViewById(R.id.gender);
        tvBirthday = findViewById(R.id.birthday);
        tvWeight = findViewById(R.id.weight);
        tvHeight = findViewById(R.id.height);
        tvPastMedicalRecord = findViewById(R.id.past_history);
        loadingProgressBar.setVisibility(View.VISIBLE);
        backId=findViewById(R.id.backId);
        backId.setOnClickListener(v -> onBackPressed());
    }
    private void setEvent(PatientModel patient) {
        if (getIntent().getExtras() != null) {
            medicalRecord = (MedicalRecordModel) getIntent().getSerializableExtra("medicalHistory");
        }
        if(medicalRecord!=null&&patient!=null){
            tvFullName.setText(patient.getHOTEN());
            tvAddress.setText(patient.getDIACHI());
            tvPhoneNumber.setText(patient.getSODIENTHOAI());
            tvCMND.setText(patient.getCMND());
            tvGender.setText(patient.getGIOITINH());
            tvBirthday.setText(patient.getNGAYSINH());
            tvHistoryRecordNumber.setText(medicalRecord.getMedicalRecordNumber());
            tvWeight.setText(medicalRecord.getWeight()+" kg");
            tvHeight.setText(medicalRecord.getHeight()+" cm");
            tvPastMedicalRecord.setText(medicalRecord.getPastMedicalHistory());
        }
        MedicalRecordModel medicalRecordModel = (MedicalRecordModel) getIntent().getSerializableExtra("medicalHistory");
        List<MedicalRecordModel> medicalRecordModels=new ArrayList<>();
        medicalRecordModels.add(medicalRecordModel);
        getEmployee(medicalRecordModels);
        loadingProgressBar.setVisibility(View.GONE);
    }
    private void getPatient() {
        userDao= db.userDao();
        userDao.getLogin().getUsername();
        API.apiService.getPatient(userDao.getLogin().getUsername()).enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if(response.code()==200&response.body()!=null){
                    setEvent(response.body());
                }
            }
            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                System.out.println("");
            }
        });

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
