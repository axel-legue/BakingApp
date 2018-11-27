package com.legue.axel.bankingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.adapter.RecipeAdapter;
import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.DataBaseUtils;
import com.legue.axel.bankingapp.database.ViewModel.RecipeViewModel;
import com.legue.axel.bankingapp.database.model.Recipe;

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

    // The Idling Resource which will be null in production.
//    @Nullable
//    private SimpleIdlingResource mIdlingResource;

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
//        mIdlingResource = new SimpleIdlingResource();
        initData();

    }


    private void initData() {

//        if (mIdlingResource != null) {
//            mIdlingResource.setIdleState(false);
//        }

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
//                mIdlingResource.setIdleState(true);
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

//    /**
//     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
//     */
//    @VisibleForTesting
//    @NonNull
//    public IdlingResource getIdlingResource() {
//        if (mIdlingResource == null) {
//            mIdlingResource = new SimpleIdlingResource();
//        }
//        return mIdlingResource;
//    }
}
