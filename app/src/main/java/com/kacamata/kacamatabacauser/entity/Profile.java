package com.kacamata.kacamatabacauser.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class Profile {
    @PrimaryKey
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "process")
    private String process;

    public Profile(@NonNull String phone, String name, String address, String process) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.process = process;
    }

    @Ignore
    public Profile() {
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    public void setPhone(@NonNull String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
