package com.legue.axel.bankingapp.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity()
public class Recipe {

    @PrimaryKey()
    private int id;
    @SerializedName("name")
    private String title;
    private int servings;
    private String image;

    @Ignore
    public Recipe() {
    }

    public Recipe(int id, String title, int servings, String image) {
        this.id = id;
        this.title = title;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
