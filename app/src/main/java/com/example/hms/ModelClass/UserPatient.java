package com.example.hms.ModelClass;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserPatient implements Serializable {
    @PrimaryKey
    @NotNull
    @SerializedName("ID")
    private Integer id;
    @SerializedName("PASSWORD")
    private String password;
    @SerializedName("HINHANH")
    private String image;
    @SerializedName("CMND")
    private String CMND;

    public UserPatient(@NotNull Integer id, String password, String image, String CMND) {
        this.id = id;
        this.password = password;
        this.image = image;
        this.CMND = CMND;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }
}
