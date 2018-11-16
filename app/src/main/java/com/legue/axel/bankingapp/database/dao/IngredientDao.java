package com.legue.axel.bankingapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.legue.axel.bankingapp.database.model.Ingredient;
import com.legue.axel.bankingapp.database.model.Recipe;

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

    @Query("SELECT * FROM Ingredient WHERE recipeId =:recipeId")
    LiveData<List<Ingredient>> getIngredientsByRecipeId(int recipeId);

    @Query("SELECT Ingredient.*,Recipe.id as recipe_id, Recipe.title as recipe_title, Recipe.servings as recipe_servings, Recipe.image as recipe_image FROM Recipe LEFT OUTER JOIN Ingredient ON Recipe.id == Ingredient.id WHERE Recipe.id = :recipeId")
    LiveData<List<RecipeIngredient>> getRecipeIngredients(int recipeId);

    class RecipeIngredient {
        @Embedded(prefix = "recipe_")
        Recipe recipe;
        @Embedded
        Ingredient ingredient;
    }
}
