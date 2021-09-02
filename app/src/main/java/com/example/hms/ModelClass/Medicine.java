package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Medicine implements Serializable {
    @SerializedName("name")
    private String nameMedicine;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private long price;

    public Medicine(String nameMedicine, int quantity, long price) {
        this.nameMedicine = nameMedicine;
        this.quantity = quantity;
        this.price = price;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public void setNameMedicine(String nameMedicine) {
        this.nameMedicine = nameMedicine;
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
}
