package com.example.hms.ui.patient.prescription;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.DetailPrescription;
import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.MedicalHistoryModel;
import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.R;
import com.example.hms.service.API;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPrescriptionActivity  extends AppCompatActivity {
    private MedicalHistoryModel medicalHistoryModel=null;
    private DetailPrescriptionAdapter detailPrescriptionAdapter;
    private TextView backId,tvDoctorName,tvDate,tvMedicalInstruction,tvTotal;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prescription);
        setControl();
        createApdater();
        setEvent();
    }
    private void setControl() {
        recyclerView=findViewById(R.id.detail_prescription_recycle_view);
        tvDoctorName= findViewById(R.id.doctor_name);
        tvDate= findViewById(R.id.date_exam);
        tvMedicalInstruction= findViewById(R.id.instruction);
        tvTotal= findViewById(R.id.total_prescription);
        backId=findViewById(R.id.backId);
        backId.setOnClickListener(v -> onBackPressed());
    }
    private void setEvent() {
        if (getIntent().getExtras() != null) {
            medicalHistoryModel = (MedicalHistoryModel) getIntent().getSerializableExtra("medicalHistory");
        }
        if(medicalHistoryModel!=null){

            getEmployee(medicalHistoryModel);
        }

    }
    private void getEmployee(MedicalHistoryModel medicalHistoryModel){
        API.apiService.getAllEmployees().enqueue(new Callback<List<EmployeeModel>>() {
            @Override
            public void onResponse(Call<List<EmployeeModel>> call, Response<List<EmployeeModel>> response) {
                if(response.body()!=null&&response.code()==200){
                    List<EmployeeModel> employeeModels =response.body();
                    setDetailPrescriptionAdapter(medicalHistoryModel,employeeModels);
                }
            }

            @Override
            public void onFailure(Call<List<EmployeeModel>> call, Throwable t) {
                System.out.println("loi");
                //loadingProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    private void createApdater(){
        Intent intent = new Intent(this, MedicineActivity.class);
           detailPrescriptionAdapter = new DetailPrescriptionAdapter(detailPrescription -> {
            intent.putExtra("detailPrescription", detailPrescription);
            startActivity(intent);
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(detailPrescriptionAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private String getDoctorName(List<EmployeeModel> employeeModel,String maBS){
        for (EmployeeModel employee:employeeModel){
            if (maBS.equals(employee.getEmployeeNumber())){
                return employee.getFullName();
            }
        }
        return "";
    }
    private void setDetailPrescriptionAdapter(MedicalHistoryModel medicalHistoryModel,List<EmployeeModel> employeeModel){

        String name=getDoctorName(employeeModel,medicalHistoryModel.getDoctorNumber());
        tvDoctorName.setText(name);
        tvDate.setText(medicalHistoryModel.getDateExam());
        try{
            tvTotal.setText(String.valueOf(medicalHistoryModel.getPrescription().getDetailPrescriptionList().size()));
            tvMedicalInstruction.setText(medicalHistoryModel.getPrescription().getMedicalInstruction());
            detailPrescriptionAdapter.setPrescriptions(medicalHistoryModel.getPrescription().getDetailPrescriptionList());
        }catch (Exception e){
            tvTotal.setText("0");
        }

    }
}
