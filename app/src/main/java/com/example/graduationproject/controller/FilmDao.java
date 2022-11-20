package com.example.graduationproject.controller;

import androidx.room.Dao;
import androidx.room.DatabaseView;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.graduationproject.model.FilmBean;
import com.example.graduationproject.model.FilmSaveBean;

import java.util.List;

@Dao
public interface FilmDao {
    @Query("SELECT * FROM filmsavebean")
    List<FilmSaveBean> getAll();

    @Query("SELECT COUNT(*) FROM filmsavebean")
    int getCount();

    @Query("SELECT * FROM filmsavebean where iswatch=:is")
    List<FilmSaveBean> getWatch(boolean... is);

    @Query("SELECT * FROM filmsavebean where islove=:is")
    List<FilmSaveBean> getLove(boolean... is);

    @Query("SELECT * FROM filmsavebean where uid=:uid")
    FilmSaveBean getIdDetail(int... uid);

    @Query("SELECT * FROM filmsavebean where film_name LIKE '%' || :name || '%'" )
    List<FilmSaveBean> getNameFilm(String... name);

    @Insert
    void insertAll(FilmSaveBean... filmSaveBeans);

    @Delete
    void delete(FilmSaveBean filmsavebean);

    @Update
    void upData(FilmSaveBean filmSaveBean);


//    @Query("DELETE from filmsavebean where uid = 1")
//    void deleteAll();

}
