package com.legue.axel.bankingapp.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.adapter.RecipeAdapter;
import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.DataBaseUtils;
import com.legue.axel.bankingapp.database.ViewModel.RecipeViewModel;
import com.legue.axel.bankingapp.database.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeActivity extends AppCompatActivity implements RecipeAdapter.RecipeListener {

    private static final String TAG = RecipeActivity.class.getName();

    @BindView(R.id.rv_recipe)
    RecyclerView recipeRecyclerView;

    private List<Recipe> recipeList;
    private RecipeAdapter recipeAdapter;


    // TODO : Create Layout For Landscape Mode
    // TODO : Check Internet / Display Message if No internet for Video
    // TODO : Add Widget
    // TODO : Add UI TEST

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        BakingDatabase mDatabase = BakingDatabase.getsInstance(this);
//        AppExecutors.getInstance().getDiskIO().execute(() -> mDatabase.clearAllTables());

        SharedPreferences preferences = getSharedPreferences("com.legue.axel.bankingapp", MODE_PRIVATE);
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

    private boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }
}
