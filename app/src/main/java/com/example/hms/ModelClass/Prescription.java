package com.example.hms.ModelClass;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Prescription {
    @SerializedName("MATOA")
    @PrimaryKey
    String prescriptionNumber;
    @SerializedName("NGAYLAP")
    long dateCreated;
    @SerializedName("YLENH")
    String medicalInstruction;
    @SerializedName("CTKHAM_ID")
    Integer detailMedicalExamID;
    @SerializedName("detailPrescriptions")
    List<DetailPrescription> detailPrescriptionList;

    public Prescription(String prescriptionNumber, long dateCreated, String medicalInstruction, Integer detailMedicalExamID, List<DetailPrescription> detailPrescriptionList) {
        this.prescriptionNumber = prescriptionNumber;
        this.dateCreated = dateCreated;
        this.medicalInstruction = medicalInstruction;
        this.detailMedicalExamID = detailMedicalExamID;
        this.detailPrescriptionList = detailPrescriptionList;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMedicalInstruction() {
        return medicalInstruction;
    }

    public void setMedicalInstruction(String medicalInstruction) {
        this.medicalInstruction = medicalInstruction;
    }

    public Integer getDetailMedicalExamID() {
        return detailMedicalExamID;
    }

    public void setDetailMedicalExamID(Integer detailMedicalExamID) {
        this.detailMedicalExamID = detailMedicalExamID;
    }

    public List<DetailPrescription> getDetailPrescriptionList() {
        return detailPrescriptionList;
    }

    public void setDetailPrescriptionList(List<DetailPrescription> detailPrescriptionList) {
        this.detailPrescriptionList = detailPrescriptionList;
    }
}
