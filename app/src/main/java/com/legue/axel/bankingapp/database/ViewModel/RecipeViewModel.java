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

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        BakingDatabase bakingDatabase = BakingDatabase.getsInstance(this.getApplication());
        recipeList = bakingDatabase.recipeDao().getAllRecipe();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeList;
    }
}
