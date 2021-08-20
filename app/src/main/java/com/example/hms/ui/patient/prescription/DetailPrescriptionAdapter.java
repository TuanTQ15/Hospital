package com.example.hms.ui.patient.prescription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.DetailPrescription;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetailPrescriptionAdapter extends
        RecyclerView.Adapter<DetailPrescriptionAdapter.ViewHolder> {
    private List<DetailPrescription> detailPrescriptionList;
    private ItemClick mItemClick;

    // Pass in the contact array into the constructor
    public DetailPrescriptionAdapter(ItemClick itemClick) {
        mItemClick = itemClick;
        detailPrescriptionList = new ArrayList<>();
    }

    public void setPrescriptions(List<DetailPrescription> detailPrescriptions) {
        this.detailPrescriptionList = detailPrescriptions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView tvPrescriptionNumber,tvDrugNumber,tvQuantity,tvPrice,tvInstruction;
        public CardView prescriptionContainer;
        private ItemClick mItemClick;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, ItemClick itemClick) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mItemClick = itemClick;
            tvPrescriptionNumber =itemView.findViewById(R.id.prescription_number);
            tvDrugNumber =itemView.findViewById(R.id.drug_number);
            tvQuantity =itemView.findViewById(R.id.quantity);
            tvPrice =itemView.findViewById(R.id.price);
            tvInstruction =itemView.findViewById(R.id.instruction);
            prescriptionContainer=itemView.findViewById(R.id.container);
        }

        public void bind(DetailPrescription detailPrescription) {
            tvPrescriptionNumber.setText(detailPrescription.getPrescriptionNumber());
            tvDrugNumber.setText(detailPrescription.getMedicineNumber());
            tvQuantity.setText(String.valueOf(detailPrescription.getQuantity()));
            tvPrice.setText(String.valueOf(detailPrescription.getPrice()));
            tvInstruction.setText(detailPrescription.getInstructionUse());
            prescriptionContainer.setOnClickListener((v) -> {
                mItemClick.click(detailPrescription);
            });
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_detail_prescription, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, mItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        DetailPrescription contact = detailPrescriptionList.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return detailPrescriptionList.size();
    }

    public interface ItemClick {
        void click(DetailPrescription contact);

    }

}
