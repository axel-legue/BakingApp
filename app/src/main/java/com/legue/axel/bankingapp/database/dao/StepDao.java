package com.legue.axel.bankingapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.legue.axel.bankingapp.database.model.Step;

import java.util.List;

@Dao
public interface StepDao {

    @Query("SELECT * FROM Step ORDER BY id")
    LiveData<List<Step>> getAllStep();

    @Query("SELECT * FROM Step WHERE id = :stepId")
    LiveData<Step> getStepById(int stepId);

    @Insert
    void insertStep(Step step);

    @Insert
    void insertAllStep(List<Step> stepList);

    @Delete
    void deleteStep(Step step);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStep(Step step);

    @Query("SELECT * FROM step WHERE recipe_id = :recipeId")
    LiveData<List<Step>> getRecipeSteps(int recipeId);

}
