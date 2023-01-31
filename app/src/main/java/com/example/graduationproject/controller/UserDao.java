package com.example.graduationproject.controller;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.model.UserData;

import java.util.List;

/**
 * @author luowen
 * @date 2023/1/17
 * des
 **/
@Dao
public interface UserDao {
    @Query("SELECT * FROM userdata")
    List<UserData> getAll();

    @Query("SELECT COUNT(*) FROM userdata")
    int getCount();

    @Query("SELECT * FROM userdata where phone =:phone")
    UserData getUserData(String... phone);

    @Insert
    void insertData(UserData userdata);

    @Delete
    void delete(UserData userdata);

    @Update
    void upData(UserData userdata);
}
