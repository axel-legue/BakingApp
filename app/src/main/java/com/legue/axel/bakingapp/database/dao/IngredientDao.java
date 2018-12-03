package com.legue.axel.bakingapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.legue.axel.bakingapp.database.model.Ingredient;

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

    @Query("SELECT * FROM Ingredient WHERE recipe_id = :recipeId")
    List<Ingredient> getRecipeIngredientsWidget(int recipeId);

}
