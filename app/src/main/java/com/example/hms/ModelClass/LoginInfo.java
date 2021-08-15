package com.example.hms.ModelClass;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "userLogin")
public class LoginInfo {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name="access_token")
    private String access_token;
    @ColumnInfo(name = "account_role")
    private String account_role;
    @ColumnInfo(name = "token_type")
    private String token_type;

    public LoginInfo(@NotNull String access_token, String account_role, String token_type) {
        this.access_token = access_token;
        this.account_role = account_role;
        this.token_type = token_type;
    }

    @NotNull
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(@NotNull String access_token) {
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
}
