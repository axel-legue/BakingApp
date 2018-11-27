package com.legue.axel.bankingapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.legue.axel.bankingapp.database.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Recipe ORDER BY id")
    LiveData<List<Recipe>> getAllRecipe();

    @Query("SELECT * FROM Recipe WHERE id = :recipeId")
    LiveData<Recipe> getRecipeById(int recipeId);

    @Insert
    void insertRecipe(Recipe recipe);

    @Insert
    void insertAllRecipe(List<Recipe> recipeList);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

}
