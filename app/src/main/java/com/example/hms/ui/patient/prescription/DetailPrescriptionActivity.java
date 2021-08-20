package com.example.hms.ui.patient.prescription;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.DetailPrescription;
import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.R;

import java.util.List;

public class DetailPrescriptionActivity  extends AppCompatActivity {
    private TextView backId;
    private PrescriptionModel prescription=null;
    private DetailPrescriptionAdapter detailPrescriptionAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_prescription);
        setControl();
        createApdater();
        setEvent();
    }

    private void setEvent() {
        if (getIntent().getExtras() != null) {
            prescription = (PrescriptionModel) getIntent().getSerializableExtra("prescription");
        }
        if(prescription!=null){
            setDetailPrescriptionAdapter(prescription.getDetailPrescriptionList());
        }
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

    private void setControl() {
        recyclerView=findViewById(R.id.detail_prescription_recycle_view);
        backId =findViewById(R.id.backId);
        backId.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    private void setDetailPrescriptionAdapter(List<DetailPrescription> detailPrescriptionList){
        detailPrescriptionAdapter.setPrescriptions(detailPrescriptionList);
    }
}
