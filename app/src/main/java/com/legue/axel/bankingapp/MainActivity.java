package com.legue.axel.bankingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.DataBaseUtils;
import com.legue.axel.bankingapp.database.RecipeAdapter;
import com.legue.axel.bankingapp.database.RecipeViewModel;
import com.legue.axel.bankingapp.database.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private BakingDatabase mDatabase;
    private DataBaseUtils dataBaseUtils;

    @BindView(R.id.rv_recipe)
    RecyclerView mRecipeRecyclerView;
    private List<Recipe> recipeList;
    private RecipeViewModel recipeViewModel;
    private RecipeAdapter recipeAdapter;

    RecipeAdapter.RecipeListener recipeListener = new RecipeAdapter.RecipeListener() {
        @Override
        public void recipeSelected(Recipe recipe) {
            Log.i(TAG, "recipeSelected: " + recipe.getTitle());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initData();

        mDatabase = BakingDatabase.getsInstance(this);
        AppExecutors.getInstance().getDiskIO().execute(() -> mDatabase.clearAllTables());

        dataBaseUtils = new DataBaseUtils(this, mDatabase);
        dataBaseUtils.fillDatabase();
    }


    private void initData() {
        if (recipeList == null) {
            recipeList = new ArrayList<>();
        }
        recipeAdapter = new RecipeAdapter(this, recipeList, recipeListener);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipeRecyclerView.setAdapter(recipeAdapter);
        mRecipeRecyclerView.setHasFixedSize(true);


        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipeList().observe(this, recipes -> {
            if (recipes != null && recipes.size() > 0) {
                recipeList.clear();
                recipeList.addAll(recipes);
                recipeAdapter.notifyDataSetChanged();
            }
        });

    }

}
