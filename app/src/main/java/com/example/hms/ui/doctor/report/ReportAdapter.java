package com.example.hms.ui.doctor.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.EmployeeModel;
import com.example.hms.ModelClass.MedicalHistoryModel;
import com.example.hms.ModelClass.StatisticTreament;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {
    private List<StatisticTreament> statisticTreamentList;
    public Button btnDetail;
    // Pass in the contact array into the constructor
    public ReportAdapter() {
        statisticTreamentList = new ArrayList<>();
    }

    public void setReport(List<StatisticTreament> statisticTreamentList) {
        this.statisticTreamentList = statisticTreamentList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvDepartmentName, tvQuantity;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvDepartmentName =itemView.findViewById(R.id.r_name);
            tvQuantity =itemView.findViewById(R.id.r_quantity);
        }

        public void bind(StatisticTreament statisticTreament) {
            tvDepartmentName.setText(statisticTreament.getNameDepartment());
            tvQuantity.setText(String.valueOf(statisticTreament.getQuantity()));
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_report, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        StatisticTreament statisticTreament = statisticTreamentList.get(position);
        holder.bind(statisticTreament);
    }

    @Override
    public int getItemCount() {
        return statisticTreamentList.size();
    }

    public interface ItemClick {
        void click(MedicalHistoryModel medicalHistoryModel);

    }
}
