package com.example.hms.ui.patient.dashboard.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.DetailPrescription;
import com.example.hms.ModelClass.Medicine;
import com.example.hms.ModelClass.MedicineModel;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MedicineAdapter extends
        RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<Medicine> medicines;
    private ItemClick mItemClick;

    // Pass in the contact array into the constructor
    public MedicineAdapter(ItemClick itemClick) {
        mItemClick = itemClick;
        medicines = new ArrayList<>();
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView tvName,tvQuantity,tvPrice, tvTotal;
        public CardView container;
        private ItemClick mItemClick;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, ItemClick itemClick) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mItemClick = itemClick;
            tvName =itemView.findViewById(R.id.name);
            tvTotal = itemView.findViewById(R.id.total);
            tvQuantity =itemView.findViewById(R.id.quantity);
            tvPrice =itemView.findViewById(R.id.price);
            container=itemView.findViewById(R.id.container);
        }

        public void bind(Medicine medicine) {
            DecimalFormat df = new DecimalFormat("###,### VNĐ");
            tvQuantity.setText(String.valueOf(medicine.getQuantity()));
            tvPrice.setText(df.format(medicine.getPrice()));
            tvName.setText(medicine.getNameMedicine());
            tvTotal.setText(df.format(medicine.getQuantity()*medicine.getPrice()));
            container.setOnClickListener((v) -> {
                mItemClick.click(medicine);
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
        View contactView = inflater.inflate(R.layout.item_medicine, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, mItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Medicine medicine = medicines.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public interface ItemClick {
        void click(Medicine medicine);

    }

}
