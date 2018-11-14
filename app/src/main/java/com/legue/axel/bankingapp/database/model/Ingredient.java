package com.legue.axel.bankingapp.database.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity()
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    public Ingredient(int id, int quantity, String measure, String name) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
}
