package com.legue.axel.bankingapp.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.model.Step;

import java.util.List;

public class StepViewModel extends AndroidViewModel {

    LiveData<List<Step>> recipeSteps;
    BakingDatabase database;


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
