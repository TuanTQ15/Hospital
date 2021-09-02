package com.example.hms.ModelClass;

import androidx.room.PrimaryKey;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Room implements Serializable {
    @SerializedName("number_room")
    private String numberRoom;
    @SerializedName("number_bed")
    private String numberBed;
    @SerializedName("price")
    private int price;
    @SerializedName("date_checkin")
    private long dateCheckin;
    @SerializedName("date_checkout")
    private long dateCheckout;
    @SerializedName("total_day")
    private int totalDay;
    @SerializedName("total_room_fee")
    private int totalRoomFee;

    public Room(String numberRoom, String numberBed, int price, long dateCheckin, long dateCheckout, int totalDay, int totalRoomFee) {
        this.numberRoom = numberRoom;
        this.numberBed = numberBed;
        this.price = price;
        this.dateCheckin = dateCheckin;
        this.dateCheckout = dateCheckout;
        this.totalDay = totalDay;
        this.totalRoomFee = totalRoomFee;
    }

    public String getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(String numberRoom) {
        this.numberRoom = numberRoom;
    }

    public String getNumberBed() {
        return numberBed;
    }

    public void setNumberBed(String numberBed) {
        this.numberBed = numberBed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDateCheckin() {
        return DateUtil.convertTime(this.dateCheckin,"dd/MM/yyyy HH:mm:ss");
    }

    public void setDateCheckin(long dateCheckin) {
        this.dateCheckin = dateCheckin;
    }

    public String getDateCheckout() {
        return DateUtil.convertTime(this.dateCheckout,"dd/MM/yyyy HH:mm:ss");
    }

    public void setDateCheckout(long dateCheckout) {
        this.dateCheckout = dateCheckout;
    }

    public int getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(int totalDay) {
        this.totalDay = totalDay;
    }

    public int getTotalRoomFee() {
        return totalRoomFee;
    }

    public void setTotalRoomFee(int totalRoomFee) {
        this.totalRoomFee = totalRoomFee;
    }
}
