package com.example.hms.ui.patient.dashboard.medicalrecord;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.R;
import com.example.hms.dao.AppDatabase;
import com.example.hms.dao.userLoginDAO;
import com.example.hms.service.API;
import com.example.hms.service.MyApplication;
import com.example.hms.ui.patient.dashboard.medicalhistory.MedicalHistoryActivity;
import com.example.hms.ui.patient.prescription.MedicineActivity;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalRecordActivity extends AppCompatActivity {

    private MedicalRecordAdapter medicalHistoryAdapter;
    private RecyclerView recyclerView;
    private TextView backId;
    private ProgressBar loadingProgressBar;
    private AppDatabase db= MyApplication.getDb();
    private userLoginDAO userDao;
    private TextView tvFullName,tvAddress,tvPhoneNumber,tvBHYT,tvCMND,
            tvGender, tvBirthday,tvWeight,tvHeight ,tvPastMedicalRecord,tvHistoryRecordNumber;
    public ImageView imagePatient;
    private PatientModel patient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);
        setControl();
        createApdater();
    }

    private void createApdater(){
        Intent intent = new Intent(this, DetailMedicalRecordActivity.class);
        medicalHistoryAdapter = new MedicalRecordAdapter(medicalHistory -> {
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
        recyclerView=findViewById(R.id.medical_record_recycle_view);
        loadingProgressBar = findViewById(R.id.loading);
        //tvHistoryRecordNumber = findViewById(R.id.medical_record_number);
        tvFullName = findViewById(R.id.full_name);
        tvAddress = findViewById(R.id.address);
        tvPhoneNumber = findViewById(R.id.phoneNumber);
        tvBHYT = findViewById(R.id.bhyt);
        tvCMND = findViewById(R.id.cmnd);
        tvGender = findViewById(R.id.gender);
        tvBirthday = findViewById(R.id.birthday);
        //tvPastMedicalRecord = findViewById(R.id.past_medical_record);
        imagePatient = findViewById(R.id.image_patient);
        loadingProgressBar.setVisibility(View.VISIBLE);
        backId=findViewById(R.id.backId);
        backId.setOnClickListener(v -> onBackPressed());
        userDao= db.userDao();
        API.apiService.getAllMedicalRecord(userDao.getLogin().getUsername()).enqueue(new Callback<List<MedicalRecordModel>>() {
            @Override
            public void onResponse(Call<List<MedicalRecordModel>> call, Response<List<MedicalRecordModel>> response) {
                if(response.code()==200&&response.body()!=null){
                    List<MedicalRecordModel> medicalRecordModels =response.body();
                    getPatient(medicalRecordModels);
                }
            }

            @Override
            public void onFailure(Call<List<MedicalRecordModel>> call, Throwable t) {
                System.out.println("loi");
            }
        });

    }
    private void setEvent(PatientModel patient) {
        tvFullName.setText(patient.getHOTEN());
        tvAddress.setText(patient.getDIACHI());
        tvPhoneNumber.setText(patient.getSODIENTHOAI());
        tvBHYT.setText(patient.getBHYT());
        tvCMND.setText(patient.getCMND());
        tvGender.setText(patient.getGIOITINH());
        tvBirthday.setText(patient.getNGAYSINH());
        setImage(imagePatient,userDao.getLogin().getImage_url());
    }
    private void setImage(ImageView  imageView,String uri) {
        Glide.with(this).load(uri)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .error(R.drawable.default_user)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imageView);
    }
    private void getPatient(List<MedicalRecordModel> medicalRecordModels) {
        userDao= db.userDao();
        userDao.getLogin().getUsername();
        API.apiService.getPatient(userDao.getLogin().getUsername()).enqueue(new Callback<PatientModel>() {
            @Override
            public void onResponse(Call<PatientModel> call, Response<PatientModel> response) {
                if(response.code()==200&response.body()!=null){
                    setEvent(response.body());
                    setMedicalHistoryAdapter(medicalRecordModels);
                }
            }
            @Override
            public void onFailure(Call<PatientModel> call, Throwable t) {
                System.out.println("");
            }
        });

    }

    private void setMedicalHistoryAdapter(List<MedicalRecordModel> medicalRecordModels) {
        if (medicalRecordModels.size() > 0) {
            Collections.sort(medicalRecordModels, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        }
        medicalHistoryAdapter.setMedicalRecordAdapter(medicalRecordModels);
        loadingProgressBar.setVisibility(View.GONE);
    }
}
