package com.example.hms.ModelClass;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserDoctor implements Serializable {
    @PrimaryKey
    @NotNull
    @SerializedName("ID")
    private Integer id;
    @SerializedName("MANV")
    private String employeeNumber;
    @SerializedName("PASSWORD")
    private String password;
    @SerializedName("HINHANH")
    private String image;
    @SerializedName("USERNAME")
    private String USERNAME;

    public UserDoctor(@NotNull Integer id, String employeeNumber, String password, String image, String USERNAME) {
        this.id = id;
        this.employeeNumber = employeeNumber;
        this.password = password;
        this.image = image;
        this.USERNAME = USERNAME;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }
}
