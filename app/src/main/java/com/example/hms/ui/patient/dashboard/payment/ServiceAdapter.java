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
import com.example.hms.ModelClass.Services;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends
        RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private List<Services> services;
    private ItemClick mItemClick;

    // Pass in the contact array into the constructor
    public ServiceAdapter(ItemClick itemClick) {
        mItemClick = itemClick;
        services = new ArrayList<>();
    }

    public void setServices(List<Services> services) {
        this.services = services;
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

        public void bind(Services service) {
            DecimalFormat df = new DecimalFormat("###,### VNĐ");
            tvQuantity.setText(String.valueOf(service.getQuantity()));
            tvPrice.setText(df.format(service.getPrice()));
            tvName.setText(service.getName());
            tvTotal.setText(df.format(service.getQuantity()*service.getPrice()));
            container.setOnClickListener((v) -> {
                mItemClick.click(service);
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
        View contactView = inflater.inflate(R.layout.item_service, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, mItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Services service = services.get(position);
        holder.bind(service);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public interface ItemClick {
        void click(Services contact);

    }

}
