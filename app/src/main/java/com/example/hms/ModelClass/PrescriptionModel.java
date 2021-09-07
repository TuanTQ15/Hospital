package com.example.hms.ModelClass;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class PrescriptionModel implements Serializable {

    @SerializedName("MATOA")
    @PrimaryKey
    String prescriptionNumber;
    @SerializedName("YLENH")
    String medicalInstruction;
    @SerializedName("CTKHAM_ID")
    Integer detailMedicalExamID;
    @SerializedName("detailPrescriptions")
    List<DetailPrescription> detailPrescriptionList;

    public PrescriptionModel(String prescriptionNumber, String medicalInstruction, Integer detailMedicalExamID, List<DetailPrescription> detailPrescriptionList) {
        this.prescriptionNumber = prescriptionNumber;
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
        if(detailPrescriptionList!=null){
            return detailPrescriptionList;
        }
        else {
            return Collections.emptyList();
        }
    }

    public void setDetailPrescriptionList(List<DetailPrescription> detailPrescriptionList) {
        this.detailPrescriptionList = detailPrescriptionList;
    }
}
