package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailPrescription {
    @SerializedName("MATOA")
    String prescriptionNumber;
    @SerializedName("MATHUOC")
    String medicineNumber;
    @SerializedName("SOLUONG")
    Integer quantity;
    @SerializedName("CACHDUNG")
    String instructionUse;
    @SerializedName("DONGIA")
    Integer price;

    public DetailPrescription(String prescriptionNumber, String medicineNumber, Integer quantity, String instructionUse, Integer price) {
        this.prescriptionNumber = prescriptionNumber;
        this.medicineNumber = medicineNumber;
        this.quantity = quantity;
        this.instructionUse = instructionUse;
        this.price = price;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public String getMedicineNumber() {
        return medicineNumber;
    }

    public void setMedicineNumber(String medicineNumber) {
        this.medicineNumber = medicineNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getInstructionUse() {
        return instructionUse;
    }

    public void setInstructionUse(String instructionUse) {
        this.instructionUse = instructionUse;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
