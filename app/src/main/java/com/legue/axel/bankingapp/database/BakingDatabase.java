package com.legue.axel.bankingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.legue.axel.bankingapp.database.dao.IngredientDao;
import com.legue.axel.bankingapp.database.dao.RecipeDao;
import com.legue.axel.bankingapp.database.dao.StepDao;
import com.legue.axel.bankingapp.database.model.Ingredient;
import com.legue.axel.bankingapp.database.model.Recipe;
import com.legue.axel.bankingapp.database.model.Step;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1, exportSchema = false)
public abstract class BakingDatabase extends RoomDatabase {

    private static String TAG = BakingDatabase.class.getName();
    private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "BakingAppDatabase";
    private static BakingDatabase sInstance;

    public static BakingDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creation of database");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(), BakingDatabase.class, BakingDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RecipeDao recipeDao();

    public abstract IngredientDao ingredientDao();

    public abstract StepDao stepDao();

}
