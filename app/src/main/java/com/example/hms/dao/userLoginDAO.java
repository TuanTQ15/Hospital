package com.example.hms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hms.ModelClass.LoginInfoModel;


@Dao
public interface userLoginDAO {
    @Query("select * from userLogin")
    LoginInfoModel getLogin();
    @Insert
    void insertAll(LoginInfoModel... loginInfoModel);
    @Query("DELETE FROM userLogin")
    void deleteAllFromTable();
    @Delete
    void delete(LoginInfoModel loginInfoModel);
}
