package com.example.hms.ModelClass;

import androidx.room.PrimaryKey;

import com.example.hms.util.DateUtil;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class EmployeeModel implements Serializable {
    @PrimaryKey
    @NotNull
    @SerializedName("MANV")
    private String employeeNumber;
    @SerializedName("MAKHOA")
    private String departmentNumber;
    @SerializedName("HOTEN")
    private String fullName;
    @SerializedName("GIOITINH")
    private String gender;
    @SerializedName("DIACHI")
    private String address;
    @SerializedName("CMND")
    private String CMND;
    @SerializedName("NGAYSINH")
    private long birthday;
    @SerializedName("HINHANH")
    private String imageUrl;
    @SerializedName("CHUCVU")
    private String position;
    @SerializedName("SODIENTHOAI")
    private String phoneNumber;
    @SerializedName("EMAIL")
    private String email;
    @SerializedName("MALOAINV")
    private String typeEmployeeNumber;

    public EmployeeModel(@NotNull String employeeNumber, String departmentNumber, String fullName,
                         String gender, String address, String CMND, long birthday,
                         String imageUrl, String position, String phoneNumber,
                         String email, String typeEmployeeNumber) {
        this.employeeNumber = employeeNumber;
        this.departmentNumber = departmentNumber;
        this.fullName = fullName;
        this.gender = gender;
        this.address = address;
        this.CMND = CMND;
        this.birthday = birthday;
        this.imageUrl = imageUrl;
        this.position = position;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.typeEmployeeNumber = typeEmployeeNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(String departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getBirthday() {
        return DateUtil.convertTime(this.birthday,"dd/MM/yyyy");
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeEmployeeNumber() {
        return typeEmployeeNumber;
    }

    public void setTypeEmployeeNumber(String typeEmployeeNumber) {
        this.typeEmployeeNumber = typeEmployeeNumber;
    }
}
