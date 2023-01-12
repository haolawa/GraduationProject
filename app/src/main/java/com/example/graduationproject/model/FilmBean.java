package com.example.graduationproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FilmBean implements Parcelable {
//      "id": "tt1375666",
//              "resultType": "Title",
//              "image": "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_Ratio0.6800_AL_.jpg",
//              "title": "Inception",
//              "description": "(2010)"
    private String id;
    private String resultType;
    private String image;
    private String title;
    private String description;

//    private String filmTime;
//    private String filmDetail;
//    private String filmReview;
//    private boolean filmWatch;
//    private boolean flimLove;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.resultType);
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.resultType = source.readString();
        this.image = source.readString();
        this.title = source.readString();
        this.description = source.readString();
    }

    public FilmBean() {
    }

    protected FilmBean(Parcel in) {
        this.id = in.readString();
        this.resultType = in.readString();
        this.image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<FilmBean> CREATOR = new Parcelable.Creator<FilmBean>() {
        @Override
        public FilmBean createFromParcel(Parcel source) {
            return new FilmBean(source);
        }

        @Override
        public FilmBean[] newArray(int size) {
            return new FilmBean[size];
        }
    };
}
