package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Services implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private long price;
    @SerializedName("day")
    private long day;

    public Services(String name, int quantity, long price, long day) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }
}
