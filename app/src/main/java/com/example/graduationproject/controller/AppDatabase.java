package com.example.graduationproject.controller;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.graduationproject.model.FilmSaveBean;
import com.example.graduationproject.model.UserData;

@Database(entities = {FilmSaveBean.class, UserData.class}, version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FilmDao filmDao();

    public abstract UserDao userDao();
}
