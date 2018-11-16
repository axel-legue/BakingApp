package com.legue.axel.bankingapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.legue.axel.bankingapp.database.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM Ingredient ORDER BY id")
    LiveData<List<Ingredient>> getAllIngredient();

    @Query("SELECT * FROM Ingredient WHERE id = :ingredientId")
    LiveData<Ingredient> getIngredientById(int ingredientId);

    @Insert
    void insertIngredient(Ingredient ingredient);

    @Insert
    void insertAllIngredient(List<Ingredient> ingredientList);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(Ingredient ingredient);

    @Query("SELECT * FROM Ingredient WHERE recipe_id = :recipeId")
    LiveData<List<Ingredient>> getRecipeIngredients(int recipeId);

}
