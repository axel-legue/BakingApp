package com.legue.axel.bankingapp.database.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.model.Ingredient;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {

    private BakingDatabase database;
    private LiveData<List<Ingredient>> allIngredientList;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        database = BakingDatabase.getsInstance(this.getApplication());
        allIngredientList = database.ingredientDao().getAllIngredient();
    }

    public LiveData<List<Ingredient>> getAllIngredientList() {
        return allIngredientList;
    }

    public LiveData<List<Ingredient>> getRecipeIngredients(int recipeId) {
        return database.ingredientDao().getRecipeIngredients(recipeId);
    }


}

