package com.legue.axel.bakingapp.database.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.legue.axel.bakingapp.database.BakingDatabase;
import com.legue.axel.bakingapp.database.model.Step;

import java.util.List;

public class StepViewModel extends AndroidViewModel {

    private LiveData<List<Step>> recipeSteps;
    private BakingDatabase database;


    public StepViewModel(@NonNull Application application) {
        super(application);
        database = BakingDatabase.getsInstance(this.getApplication());
    }

    public LiveData<List<Step>> getRecipeSteps(int recipeId) {
        recipeSteps = database.stepDao().getRecipeSteps(recipeId);
        return recipeSteps;
    }

    public LiveData<Step> getStepById(int stepId) {
        return database.stepDao().getStepById(stepId);
    }
}
