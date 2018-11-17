package com.legue.axel.bankingapp.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipe_id",
        onDelete = ForeignKey.CASCADE))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int ingredientId;
    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    @ColumnInfo(name = "recipe_id")
    private int recipeId;


    public Ingredient(int ingredientId, double quantity, String measure, String name, int recipeId) {
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
