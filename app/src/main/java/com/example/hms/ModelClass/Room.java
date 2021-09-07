package com.example.hms.ModelClass;

import androidx.room.PrimaryKey;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Room implements Serializable {
    @SerializedName("price")
    private int price;
    @SerializedName("date_checkin")
    private long dateCheckin;
    @SerializedName("date_checkout")
    private long dateCheckout;
    @SerializedName("total_day")
    private int totalDay;

    public Room( int price, long dateCheckin, long dateCheckout, int totalDay) {

        this.price = price;
        this.dateCheckin = dateCheckin;
        this.dateCheckout = dateCheckout;
        this.totalDay = totalDay;
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

}
