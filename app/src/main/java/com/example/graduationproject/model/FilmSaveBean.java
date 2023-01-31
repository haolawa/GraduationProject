package com.example.graduationproject.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.ToString;

/**
 * @author luowen
 */
@Data
@ToString
@Entity(foreignKeys = @ForeignKey(
        entity = UserData.class,
        parentColumns = "phone",
        childColumns = "uid"))
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmImg() {
        return filmImg;
    }

    public void setFilmImg(String filmImg) {
        this.filmImg = filmImg;
    }

    public String getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(String filmTime) {
        this.filmTime = filmTime;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getFilmDirectors() {
        return filmDirectors;
    }

    public void setFilmDirectors(String filmDirectors) {
        this.filmDirectors = filmDirectors;
    }

    public String getFilmPerformer() {
        return filmPerformer;
    }

    public void setFilmPerformer(String filmPerformer) {
        this.filmPerformer = filmPerformer;
    }

    public String getFilmDetail() {
        return filmDetail;
    }

    public void setFilmDetail(String filmDetail) {
        this.filmDetail = filmDetail;
    }

    public String getFilmNote() {
        return filmNote;
    }

    public void setFilmNote(String filmNote) {
        this.filmNote = filmNote;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public boolean isWatch() {
        return isWatch;
    }

    public void setWatch(boolean watch) {
        isWatch = watch;
    }
}
