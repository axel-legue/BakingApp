package com.legue.axel.bankingapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.legue.axel.bankingapp.database.model.Recipe;
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
