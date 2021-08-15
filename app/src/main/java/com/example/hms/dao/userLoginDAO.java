package com.example.hms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hms.ModelClass.LoginInfo;


@Dao
public interface userLoginDAO {
    @Query("select * from userLogin")
    LoginInfo getLogin();
    @Insert
    void insertAll(LoginInfo... loginInfo);
    @Query("DELETE FROM userLogin")
    void deleteAllFromTable();
    @Delete
    void delete(LoginInfo loginInfo);
}
