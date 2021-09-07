package com.example.hms.ModelClass;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("USERNAME")
    private String username;
    @SerializedName("PASSWORD")
    private String password;

    public ChangePassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
