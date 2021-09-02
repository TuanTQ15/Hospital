package com.example.hms.ui.patient.prescription;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;

import com.example.hms.ModelClass.PrescriptionModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends
        RecyclerView.Adapter<PrescriptionAdapter.ViewHolder> {
    private List<PrescriptionModel> mContacts;
    private ItemClick mItemClick;

    // Pass in the contact array into the constructor
    public PrescriptionAdapter(ItemClick itemClick) {
        mItemClick = itemClick;
        mContacts = new ArrayList<>();
    }

    public void setPrescriptions(List<PrescriptionModel> prescriptions) {
        this.mContacts = prescriptions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvMedicalExam;
        public TextView tvPrescriptionNumber;
        public TextView tvPInstruction;
        public CardView prescriptionContainer;
        private ItemClick mItemClick;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, ItemClick itemClick) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            mItemClick = itemClick;
            tvPrescriptionNumber = itemView.findViewById(R.id.prescription_number);
            tvPInstruction = itemView.findViewById(R.id.instruction);
            prescriptionContainer = itemView.findViewById(R.id.container);
        }

        public void bind(PrescriptionModel prescription) {
            tvPrescriptionNumber.setText(prescription.getPrescriptionNumber());
            tvPInstruction.setText(prescription.getMedicalInstruction());
            prescriptionContainer.setOnClickListener((v) -> {
                mItemClick.click(prescription);
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
        View contactView = inflater.inflate(R.layout.item_prescription_view, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, mItemClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        PrescriptionModel contact = mContacts.get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public interface ItemClick {
        void click(PrescriptionModel contact);
    }

    public void deleteContact(String message, PrescriptionModel contact, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setNegativeButton("Yes", (dialogInterface, i) -> {

//           ApiService.apiService.deleteContact(contact.getId()).enqueue(new Callback<Void>() {
//               @Override
//               public void onResponse(Call<Void> call, Response<Void> response) {
//                   if(response.code()==200){
//                       mItemClick.delete(v, contact);
//                   }
//               }
//
//               @Override
//               public void onFailure(Call<Void> call, Throwable t) {
//                   showNotify("xóa thất bại!!!",v);
//               }
//           });
//        });
       // builder.setPositiveButton("No", (dialogInterface, i) -> {

        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
    public void showNotify(String message, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogCustom);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }
}
