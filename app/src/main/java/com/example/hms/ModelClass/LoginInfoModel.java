package com.example.hms.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "userLogin")
public class LoginInfoModel implements Serializable {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name="access_token")
    private String access_token;
    @ColumnInfo(name = "account_role")
    private String account_role;
    @ColumnInfo(name = "token_type")
    private String token_type;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "fullname")
    private String fullname;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name = "image_url")
    private String image_url;

    public LoginInfoModel(@NotNull String access_token, String account_role, String token_type, String username, String fullname, String email, String image_url) {
        this.access_token = access_token;
        this.account_role = account_role;
        this.token_type = token_type;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.image_url = image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccount_role() {
        return account_role;
    }

    public void setAccount_role(String account_role) {
        this.account_role = account_role;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
