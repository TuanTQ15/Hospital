package com.example.hms.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class MedicalHistoryModel implements Serializable {
    @PrimaryKey
    @NotNull
    @SerializedName("CTKHAM_ID")
    private Integer id;
    @SerializedName("MABA")
    private String medicalRecordNumber;
    @SerializedName("MABS")
    private String doctorNumber;
    @SerializedName("MAYTA")
    private String nurseNumber;
    @SerializedName("NGAYKHAM")
    private long dateExam;
    @SerializedName("TINHTRANG")
    private String status;
    @SerializedName("CHANDOAN")
    private String diagnose;
    @SerializedName("prescription")
    private PrescriptionModel prescription;

    public MedicalHistoryModel(@NotNull Integer id, String medicalRecordNumber, String doctorNumber, String nurseNumber, long dateExam, String status, String diagnose, PrescriptionModel prescription) {
        this.id = id;
        this.medicalRecordNumber = medicalRecordNumber;
        this.doctorNumber = doctorNumber;
        this.nurseNumber = nurseNumber;
        this.dateExam = dateExam;
        this.status = status;
        this.diagnose = diagnose;
        this.prescription = prescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicalRecordNumber() {
        return medicalRecordNumber;
    }

    public void setMedicalRecordNumber(String medicalRecordNumber) {
        this.medicalRecordNumber = medicalRecordNumber;
    }

    public String getDoctorNumber() {
        return doctorNumber;
    }

    public void setDoctorNumber(String doctorNumber) {
        this.doctorNumber = doctorNumber;
    }

    public String getNurseNumber() {
        return nurseNumber;
    }

    public void setNurseNumber(String nurseNumber) {
        this.nurseNumber = nurseNumber;
    }

    public String getDateExam() {
        return DateUtil.convertTime(this.dateExam,"dd/MM/yyyy HH:mm:ss");
    }

    public void setDateExam(String dateExam) {
        this.dateExam = DateUtil.convertTimeToLong(dateExam);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public PrescriptionModel getPrescription() {
        if (prescription!=null){
            return prescription;
        }else {
            return null;
        }
    }

    public void setPrescription(PrescriptionModel prescription) {
        this.prescription = prescription;
    }
}
