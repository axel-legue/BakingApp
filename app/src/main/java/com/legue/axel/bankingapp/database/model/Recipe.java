package com.legue.axel.bankingapp.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity()
public class Recipe {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    private int recipeId;
    @SerializedName("name")
    private String title;
    private int servings;
    private String image;

    public Recipe(int recipeId, String title, int servings, String image) {
        this.recipeId = recipeId;
        this.title = title;
        this.servings = servings;
        this.image = image;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
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
