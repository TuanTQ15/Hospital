package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MedicineModel implements Serializable {
    @SerializedName("MATHUOC")
    private String medicineNumber;
    @SerializedName("TENTHUOC")
    private String medicineName;
    @SerializedName("CONGDUNG")
    private String uses;
    @SerializedName("MOTA")
    private String description;
    @SerializedName("HINHANH")
    private String image_url;

    public MedicineModel(String medicineNumber, String medicineName, String uses, String description, String image_url) {
        this.medicineNumber = medicineNumber;
        this.medicineName = medicineName;
        this.uses = uses;
        this.description = description;
        this.image_url = image_url;
    }

    public String getMedicineNumber() {
        return medicineNumber;
    }

    public void setMedicineNumber(String medicineNumber) {
        this.medicineNumber = medicineNumber;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
