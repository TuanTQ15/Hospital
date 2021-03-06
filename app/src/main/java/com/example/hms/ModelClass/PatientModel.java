package com.example.hms.ModelClass;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientModel implements Serializable {
    @SerializedName("CMND")
    private String CMND;
    @SerializedName("HOTEN")
    private String HOTEN;
    @SerializedName("EMAIL")
    private String EMAIL;
    @SerializedName("SODIENTHOAI") private
    String SODIENTHOAI;
    @SerializedName("DIACHI")
    private String DIACHI;
    @SerializedName("GIOITINH")
    private String GIOITINH;
    @SerializedName("NGAYSINH")
    private long NGAYSINH;
    @SerializedName("HINHANH")
    private String HINHANH;
    @SerializedName("DOITUONG")
    private String DOITUONG="Nội Trú";
    public PatientModel(String CMND, String HOTEN, String EMAIL, String SODIENTHOAI,
                        String DIACHI, String GIOITINH, long NGAYSINH, String HINHANH) {
        this.CMND = CMND;
        this.HOTEN = HOTEN;
        this.EMAIL = EMAIL;
        this.SODIENTHOAI = SODIENTHOAI;
        this.DIACHI = DIACHI;
        this.GIOITINH = GIOITINH;
        this.NGAYSINH = NGAYSINH;
        this.HINHANH = HINHANH;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getHOTEN() {
        return HOTEN;
    }

    public void setHOTEN(String HOTEN) {
        this.HOTEN = HOTEN;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getSODIENTHOAI() {
        return SODIENTHOAI;
    }

    public void setSODIENTHOAI(String SODIENTHOAI) {
        this.SODIENTHOAI = SODIENTHOAI;
    }

    public String getDIACHI() {
        return DIACHI;
    }

    public void setDIACHI(String DIACHI) {
        this.DIACHI = DIACHI;
    }

    public String getGIOITINH() {
        return GIOITINH;
    }

    public void setGIOITINH(String GIOITINH) {
        this.GIOITINH = GIOITINH;
    }

    public String getNGAYSINH() {
        return DateUtil.convertTime(this.NGAYSINH,"dd/MM/yyyy");
    }

    public void setNGAYSINH(long NGAYSINH) {
        this.NGAYSINH = NGAYSINH;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

}
