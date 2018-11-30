package com.learning.course.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.learning.database.Constans;

import java.io.Serializable;

@Entity(tableName = Constans.TABLE_COURSE)
public class Course implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "picture")
    private int picture;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "rating_bar")
    private int ratingBar;

    @ColumnInfo(name = "id_instructor")
    private int idInstructor;

    @ColumnInfo(name = "beforePrice")
    private double beforePrice;

    @ColumnInfo(name = "currentPrice")
    private double currentPrice;

    public Course() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(int ratingBar) {
        this.ratingBar = ratingBar;
    }

    public int getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(int idInstructor) {
        this.idInstructor = idInstructor;
    }

    public double getBeforePrice() {
        return beforePrice;
    }

    public void setBeforePrice(double beforePrice) {
        this.beforePrice = beforePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
