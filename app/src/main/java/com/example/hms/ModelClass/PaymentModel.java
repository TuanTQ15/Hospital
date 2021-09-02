package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PaymentModel implements Serializable {
    @SerializedName("medical_record")
    private String medicalRecord;
    @SerializedName("status")
    private int status;
    @SerializedName("advances")
    private long advances;
    @SerializedName("rooms")
    private Room rooms;
    @SerializedName("services")
    private List<Services> services;
    @SerializedName("medicines")
    private List<Medicine> medicines;

    public PaymentModel(int status,String medicalRecord, long advances, Room rooms, List<Services> services, List<Medicine> medicines) {
        this.medicalRecord=medicalRecord;
        this.status = status;
        this.advances = advances;
        this.rooms = rooms;
        this.services = services;
        this.medicines = medicines;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAdvances() {
        return advances;
    }

    public void setAdvances(long advances) {
        this.advances = advances;
    }

    public Room getRooms() {
        return rooms;
    }

    public void setRooms(Room rooms) {
        this.rooms = rooms;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}

