package com.example.hms.ui.patient.dashboard.medicalrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.MedicalHistoryModel;
import com.example.hms.ModelClass.MedicalRecordModel;
import com.example.hms.ModelClass.PatientModel;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordAdapter extends
        RecyclerView.Adapter<MedicalRecordAdapter.ViewHolder> {
    private List<MedicalRecordModel> medicalRecordModels;
    private ItemClick mItemClick;
    public TextView medicalRecordNumber;
    public TextView tvDoctorName;
    public TextView tvNurseName;
    public CardView container;
    // Pass in the contact array into the constructor
    public MedicalRecordAdapter(ItemClick itemClick) {
        mItemClick = itemClick;
        medicalRecordModels=new ArrayList<>();
    }

    public void setMedicalRecordAdapter(List<MedicalRecordModel> medicalRecordModels) {
        this.medicalRecordModels = medicalRecordModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
//        public TextView tvFullName,tvAddress,tvPhoneNumber,tvBHYT,tvCMND,
//                tvGender, tvBirthday,tvWeight,tvHeight ,tvPastMedicalRecord,tvHistoryRecordNumber;
//        public ImageView imagePatient;
        private ItemClick mItemClick;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, ItemClick itemClick) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mItemClick = itemClick;
            medicalRecordNumber = itemView.findViewById(R.id.medical_record_number);
            tvDoctorName = itemView.findViewById(R.id.doctor_name);
            tvNurseName = itemView.findViewById(R.id.nurse_name);
            container = itemView.findViewById(R.id.container);
        }
        public void bind(MedicalRecordModel medicalRecord) {
            medicalRecordNumber.setText(medicalRecord.getMedicalRecordNumber());
            tvDoctorName.setText(medicalRecord.getDate());
            tvNurseName.setText(medicalRecord.getCMND());
            container.setOnClickListener(v -> mItemClick.click(medicalRecord));
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_medical_record, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, mItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        MedicalRecordModel medicalRecordModel = medicalRecordModels.get(position);
        holder.bind(medicalRecordModel);
    }

    @Override
    public int getItemCount() {
        return medicalRecordModels.size();
    }

    public interface ItemClick {
        void click(MedicalRecordModel medicalRecord);

    }
}
