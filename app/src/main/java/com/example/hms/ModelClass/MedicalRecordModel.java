package com.example.hms.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class MedicalRecordModel implements Serializable {
    @PrimaryKey
    @NotNull
    @SerializedName("MABA")
    private String medicalRecordNumber;
    @SerializedName("MANV")
    private String employeeNumber;
    @SerializedName("CMND")
    private String CMND;
    @SerializedName("TIENSU")
    private String pastMedicalHistory;
    @SerializedName("NGAYLAP")
    private long date;
    @SerializedName("CANNANG")
    private Integer weight;
    @SerializedName("CHIEUCAO")
    private Integer height;
    @SerializedName("medicalhistorys")
    private List<MedicalHistoryModel> medicalHistorys;

    public MedicalRecordModel(@NotNull String medicalRecordNumber, String employeeNumber, String CMND,
                              String pastMedicalHistory, long date, Integer weight, Integer height,
                              List<MedicalHistoryModel> medicalHistorys) {
        this.medicalRecordNumber = medicalRecordNumber;
        this.employeeNumber = employeeNumber;
        this.CMND = CMND;
        this.pastMedicalHistory = pastMedicalHistory;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.medicalHistorys = medicalHistorys;
    }

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getPastMedicalHistory() {
        return pastMedicalHistory;
    }

    public void setPastMedicalHistory(String pastMedicalHistory) {
        this.pastMedicalHistory = pastMedicalHistory;
    }

    public String getDate() {
        return DateUtil.convertTime(this.date,"dd/MM/yyyy HH:mm:ss");
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<MedicalHistoryModel> getMedicalHistorys() {
        return medicalHistorys;
    }

    public void setMedicalHistorys(List<MedicalHistoryModel> medicalHistorys) {
        this.medicalHistorys = medicalHistorys;
    }
}
