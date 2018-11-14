package com.legue.axel.bankingapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
