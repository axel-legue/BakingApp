package com.legue.axel.bakingapp.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.legue.axel.bakingapp.Constants;
import com.legue.axel.bakingapp.IdlingResource.SimpleIdlingResource;
import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.database.BakingDatabase;
import com.legue.axel.bakingapp.database.model.Recipe;
import com.legue.axel.bakingapp.fragment.StepDetailFragment;
import com.legue.axel.bakingapp.fragment.StepsFragment;
import com.legue.axel.bakingapp.widget.RecipeWidgetProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.espresso.IdlingResource;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity implements StepsFragment.StepListener {

    private static final String TAG = StepsActivity.class.getName();

    private static final String STEP_TAG = "step_tag";
    private static final String DETAIL_TAG = "detail_tag";

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;


    private int recipeIdSelected;
    private boolean mTwoPane;
    private StepDetailFragment stepDetailFragment;

    @BindView(R.id.fab_favorite)
    private
    FloatingActionButton favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.KEY_RECIPE_ID)) {
            recipeIdSelected = intent.getIntExtra(Constants.KEY_RECIPE_ID, -1);
        }

        Fragment fragmentStep = getSupportFragmentManager().findFragmentByTag(STEP_TAG);


        StepsFragment stepsFragment;
        if (fragmentStep == null) {
            if (mIdlingResource != null) {
                mIdlingResource.setIdleState(false);
            }
            stepsFragment = new StepsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.KEY_RECIPE_ID, recipeIdSelected);
            stepsFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.step_container, stepsFragment, STEP_TAG)
                    .commit();
        } else {
            if (findViewById(R.id.detail_container) != null) {
                mTwoPane = true;
            } else {
                mTwoPane = false;
            }
            stepsFragment = (StepsFragment) fragmentStep;
        }

        Fragment fragmentDetail = getSupportFragmentManager().findFragmentByTag(DETAIL_TAG);
        if (fragmentDetail == null) {
            if (findViewById(R.id.detail_container) != null) {
                mTwoPane = true;
                stepDetailFragment = new StepDetailFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.detail_container, stepDetailFragment, DETAIL_TAG)
                        .commit();
            } else {
                mTwoPane = false;
            }
        } else {
            if (findViewById(R.id.detail_container) != null) {
                mTwoPane = true;
            } else {
                mTwoPane = false;
            }
            stepDetailFragment = (StepDetailFragment) fragmentDetail;
        }

        favoriteButton.setOnClickListener(view -> BakingDatabase.getsInstance(this).recipeDao().getRecipeById(recipeIdSelected).observe(this, recipe -> {

            SharedPreferences preferences = getSharedPreferences("com.legue.axel.bankingapp", MODE_PRIVATE);
            if (preferences.contains(Constants.KEY_FAVORITE_RECIPE)) {
                Gson gson = new Gson();
                String json = preferences.getString(Constants.KEY_FAVORITE_RECIPE, "");
                Recipe mRecipe = gson.fromJson(json, Recipe.class);
                if (recipe.getRecipeId() != mRecipe.getRecipeId()) {
                    preferences.edit()
                            .putString(Constants.KEY_FAVORITE_RECIPE, gson.toJson(recipe))
                            .apply();
                    updateWidget();

                    Snackbar.make(view, "You just saved the actual recipe as favorite", Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    Snackbar.make(view, "This recipe is already favorite", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        }));
    }

    private void updateWidget() {
        Intent intent = new Intent(getApplicationContext(), RecipeWidgetProvider.class);
        intent.setAction(Constants.ACTION_UPDATE_WIDGET);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    @Override
    public void stepSelected(int firstStepId, int lastStepId, int stepSelectedId) {
        if (!mTwoPane) {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(Constants.KEY_FIRST_STEP_ID, firstStepId);
            intent.putExtra(Constants.KEY_LAST_STEP_ID, lastStepId);
            intent.putExtra(Constants.KEY_STEPS_ID, stepSelectedId);
            startActivity(intent);
        } else {
            stepDetailFragment.updateDetails(firstStepId, lastStepId, stepSelectedId);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, RecipeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void dataLoaded() {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}
