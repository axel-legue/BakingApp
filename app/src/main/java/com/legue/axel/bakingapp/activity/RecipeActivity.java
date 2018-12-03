package com.legue.axel.bakingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.legue.axel.bakingapp.Constants;
import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.adapter.RecipeAdapter;
import com.legue.axel.bakingapp.database.BakingDatabase;
import com.legue.axel.bakingapp.database.DataBaseUtils;
import com.legue.axel.bakingapp.database.ViewModel.RecipeViewModel;
import com.legue.axel.bakingapp.database.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {

    private static final String TAG = RecipeActivity.class.getName();

    @BindView(R.id.rv_recipe)
    RecyclerView recipeRecyclerView;

    private List<Recipe> recipeList;
    private RecipeAdapter recipeAdapter;
    private SharedPreferences preferences;

    /**
     * ===========================================================================================
     * GENERAL UPDATE TO MAKE FOR A BETTER APPLICATION
     * =============================================================================================
     */
    // TODO : Check Internet / Display Message if No internet for Video
    // TODO : Better Design
    // TODO : Add Animation / Transition
    // TODO : Ability to add Recipe
    // TODO : Ability to share Recipe
    // TODO : Test Dagger 2
    // TODO : Convert to Kotlin
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        BakingDatabase mDatabase = BakingDatabase.getsInstance(this);
//        AppExecutors.getInstance().getDiskIO().execute(() -> mDatabase.clearAllTables());

        preferences = getSharedPreferences("com.legue.axel.bankingapp", MODE_PRIVATE);
        if (!preferences.contains(Constants.KEY_FIRST_RUN)) {
            preferences.edit().putBoolean(Constants.KEY_FIRST_RUN, false).apply();
            DataBaseUtils dataBaseUtils = new DataBaseUtils(this, mDatabase);
            dataBaseUtils.fillDatabase();
        }

        initData();

    }


    private void initData() {

        if (recipeList == null) {
            recipeList = new ArrayList<>();

            // Change layoutManager if tablet or not
            recipeAdapter = new RecipeAdapter(this, recipeList, this);
            if (isTablet()) {
                recipeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            } else {
                recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
            recipeRecyclerView.setAdapter(recipeAdapter);
            recipeRecyclerView.setHasFixedSize(true);

            RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
            recipeViewModel.getRecipeList().observe(this, recipes -> {

                if (recipes != null && recipes.size() > 0) {
                    // TODO : Add ProgressBar
                    recipeList.clear();
                    recipeList.addAll(recipes);
                    recipeAdapter.notifyDataSetChanged();

                    // Save to Shared Preference
                    if (!preferences.contains(Constants.KEY_FAVORITE_RECIPE)) {
                        Gson gson = new Gson();
                        String json = gson.toJson(recipeList.get(0));
                        preferences.edit()
                                .putString(Constants.KEY_FAVORITE_RECIPE, json)
                                .apply();
                    }
                }
            });
        }
    }

    @Override
    public void onRecipeSelected(int recipeId) {
        Intent intent = new Intent(this, StepsActivity.class);
        intent.putExtra(Constants.KEY_RECIPE_ID, recipeId);
        startActivity(intent);
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

}
