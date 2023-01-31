package com.example.graduationproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.ToString;

/**
 * @author luowen
 * @date 2023/1/17
 * des
 **/
@Data
@ToString
@Entity
public class UserData {
    @NonNull
    @PrimaryKey()
    private String phone;
    @ColumnInfo(name = "password")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
