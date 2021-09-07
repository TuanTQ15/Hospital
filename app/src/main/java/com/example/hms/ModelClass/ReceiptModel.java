package com.example.hms.ModelClass;

import com.google.gson.annotations.SerializedName;


public class ReceiptModel {
    @SerializedName("TONGTIEN")
    private long total;
    @SerializedName("GHICHU")
    private String note;
    @SerializedName("TIENTHUOC")
    private long medicine;
    @SerializedName("MABA")
    private String recordNumber;
    @SerializedName("TIENGIUONG")
    private long room;
    @SerializedName("TONGTAMUNG")
    private long advances;
    @SerializedName("TIENDICHVU")
    private long services;
    @SerializedName("THUCTRA")
    private long paid;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getMedicine() {
        return medicine;
    }

    public void setMedicine(long medicine) {
        this.medicine = medicine;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public long getRoom() {
        return room;
    }

    public void setRoom(long room) {
        this.room = room;
    }

    public long getAdvances() {
        return advances;
    }

    public void setAdvances(long advances) {
        this.advances = advances;
    }

    public long getServices() {
        return services;
    }

    public void setServices(long services) {
        this.services = services;
    }

    public long getPaid() {
        return paid;
    }

    public void setPaid(long paid) {
        this.paid = paid;
    }

    public ReceiptModel(long total, String note, long medicine, String recordNumber, long room, long advances, long services, long paid) {
        this.total = total;
        this.note = note;
        this.medicine = medicine;
        this.recordNumber = recordNumber;
        this.room = room;
        this.advances = advances;
        this.services = services;
        this.paid = paid;
    }
}
