package com.example.graduationproject.controller;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.graduationproject.model.FilmSaveBean;

@Database(entities = {FilmSaveBean.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FilmDao filmDao();
}
