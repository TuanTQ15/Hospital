package com.example.hms.ui.patient.prescription;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hms.ModelClass.DetailPrescription;
import com.example.hms.ModelClass.MedicineModel;
import com.example.hms.ModelClass.PrescriptionModel;
import com.example.hms.R;

public class MedicineActivity extends AppCompatActivity {
    private TextView tvUses,tvDrugNumber,tvMedicineName,tvDescription,backId;
    private ImageView imageView;
    private DetailPrescription detailPrescription;
    private MedicineModel medicine;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        setControl();
        setEvent();
    }

    private void setEvent() {
        if (getIntent().getExtras() != null) {
            detailPrescription = (DetailPrescription) getIntent().getSerializableExtra("detailPrescription");
        }
        if(detailPrescription!=null){
            medicine=detailPrescription.getMedicine();
            tvDrugNumber.setText(medicine.getMedicineNumber());
            tvMedicineName.setText(medicine.getMedicineName());
            tvUses.setText(medicine.getUses());
            tvDescription.setText(medicine.getDescription());
           
        }
        backId.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setControl() {
        tvDrugNumber =findViewById(R.id.drug_number);
        tvUses =findViewById(R.id.uses);
        tvMedicineName =findViewById(R.id.medicine_name);
        tvDescription =findViewById(R.id.description);
        imageView =findViewById(R.id.image_medicine);
        backId =findViewById(R.id.backId);
    }
}
