package com.legue.axel.bankingapp.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipe_id",
        onDelete = ForeignKey.CASCADE))
public class Ingredient implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ingredientId);
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.name);
        dest.writeInt(this.recipeId);
    }

    protected Ingredient(Parcel in) {
        this.ingredientId = in.readInt();
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.name = in.readString();
        this.recipeId = in.readInt();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
