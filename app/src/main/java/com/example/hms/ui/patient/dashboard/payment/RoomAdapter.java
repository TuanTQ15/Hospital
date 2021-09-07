package com.example.hms.ui.patient.dashboard.payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.ModelClass.Room;
import com.example.hms.ModelClass.Services;
import com.example.hms.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends
        RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Room> rooms;

    // Pass in the contact array into the constructor
    public RoomAdapter() {

        rooms = new ArrayList<>();
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private TextView tvCheckin,tvCheckout,tvTotalDay,tvRate;
        public CardView container;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvCheckin =itemView.findViewById(R.id.checkin);
            tvCheckout = itemView.findViewById(R.id.checkout);
            tvTotalDay =itemView.findViewById(R.id.number_day);
            tvRate =itemView.findViewById(R.id.rate);
        }

        public void bind(Room room) {
            DecimalFormat df = new DecimalFormat("###,### VNĐ");
            tvCheckin.setText(room.getDateCheckin());
            tvCheckout.setText(room.getDateCheckout());
            tvTotalDay.setText(String.valueOf(room.getTotalDay()));
            tvRate.setText(df.format(room.getPrice()));
        }
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_room, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


}
