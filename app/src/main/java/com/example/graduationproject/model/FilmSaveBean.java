package com.example.graduationproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class FilmSaveBean {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "film_name")
    public String filmName;
    @ColumnInfo(name = "film_img")
    public String filmImg;
    @ColumnInfo(name = "film_time")
    public String filmTime;
    @ColumnInfo(name = "film_type")
    public String filmType;
    @ColumnInfo(name = "film_directors")
    public String filmDirectors;
    @ColumnInfo(name = "film_performer")
    public String filmPerformer;
    @ColumnInfo(name = "film_detail")
    public String filmDetail;
    @ColumnInfo(name = "film_note")
    public String filmNote;
    @ColumnInfo(name = "islove")
    public boolean isLove;
    @ColumnInfo(name = "iswatch")
    public boolean isWatch;


}
