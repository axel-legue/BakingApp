package com.legue.axel.bankingapp.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.model.Recipe;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private LiveData<List<Recipe>> recipeList;
    private BakingDatabase database;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        database = BakingDatabase.getsInstance(this.getApplication());
        recipeList = database.recipeDao().getAllRecipe();

    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }

    public LiveData<Recipe> getRecipeById(int recipeId) {
        return database.recipeDao().getRecipeById(recipeId);
    }
}
