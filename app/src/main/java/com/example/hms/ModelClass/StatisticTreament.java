package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;

public class StatisticTreament {
    @SerializedName("TENKHOA")
    private String nameDepartment;
    @SerializedName("SOLUONG")
    private long quantity;

    public StatisticTreament(String nameDepartment, long quantity) {
        this.nameDepartment = nameDepartment;
        this.quantity = quantity;
    }

    public String getNameDepartment() {
        return nameDepartment;
    }

    public void setNameDepartment(String nameDepartment) {
        this.nameDepartment = nameDepartment;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
