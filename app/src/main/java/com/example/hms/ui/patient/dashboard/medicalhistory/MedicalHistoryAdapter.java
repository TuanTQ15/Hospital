package com.example.hms.ui.patient.dashboard.medicalhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.MedicalHistoryModel;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryAdapter extends
        RecyclerView.Adapter<MedicalHistoryAdapter.ViewHolder> {
    private List<MedicalHistoryModel> medicalHistorys;
    private  List<EmployeeModel> employeeModels;
    private ItemClick mItemClick;
    public Button btnDetail;
    // Pass in the contact array into the constructor
    public MedicalHistoryAdapter(ItemClick itemClick) {
        mItemClick = itemClick;
        medicalHistorys = new ArrayList<>();
    }

    public void setMedicalHistoryAdapter(List<MedicalHistoryModel> medicalHistorys, List<EmployeeModel> employeeModels) {
        this.medicalHistorys = medicalHistorys;
        this.employeeModels = employeeModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView medicalRecordNumber;
        public TextView tvDoctorName;
        public TextView tvNurseName;
        public TextView tvDiagnose;
        public TextView tvDateExam;
        public TextView tvStatus;
        private String doctorName="",nurseName="";
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
            tvDiagnose = itemView.findViewById(R.id.diagnose);
            tvDateExam = itemView.findViewById(R.id.date_exam);
            tvStatus = itemView.findViewById(R.id.status);
            btnDetail = itemView.findViewById(R.id.button);
        }

        public void bind(MedicalHistoryModel medicalHistory) {
            for(EmployeeModel employee:employeeModels){
                if(employee.getEmployeeNumber().equals(medicalHistory.getDoctorNumber()))
                    doctorName=employee.getFullName();

                if(employee.getEmployeeNumber().equals(medicalHistory.getNurseNumber()))
                    nurseName=employee.getFullName();
            }
            medicalRecordNumber.setText(String.valueOf(medicalHistory.getMedicalRecordNumber()));
            tvDoctorName.setText(doctorName);
            tvNurseName.setText(nurseName);
            tvDiagnose.setText(medicalHistory.getDiagnose());
            tvDateExam.setText(medicalHistory.getDateExam());
            tvStatus.setText(medicalHistory.getStatus());
            if (medicalHistory.getPrescription()!=null){
                btnDetail.setOnClickListener(v -> mItemClick.click(medicalHistory));
            }else{
                btnDetail.setEnabled(false);
            }

        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_medical_history, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, mItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        MedicalHistoryModel medicalHistory = medicalHistorys.get(position);
        holder.bind(medicalHistory);
    }

    @Override
    public int getItemCount() {
        return medicalHistorys.size();
    }

    public interface ItemClick {
        void click(MedicalHistoryModel medicalHistoryModel);

    }
}
