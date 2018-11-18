package com.legue.axel.bankingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.DataBaseUtils;
import com.legue.axel.bankingapp.fragment.RecipeFragment;
import com.legue.axel.bankingapp.fragment.StepDetailFragment;
import com.legue.axel.bankingapp.fragment.StepsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RecipeFragment.RecipeListener, StepsFragment.StepListener {

    private static final String TAG = MainActivity.class.getName();

    private BakingDatabase mDatabase;

    private RecipeFragment recipeFragment;
    private StepsFragment stepsFragment;
    private StepDetailFragment stepDetailFragment;
    private FragmentManager fragmentManager;

    @BindView(R.id.recipe_container)
    FrameLayout mRecipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mDatabase = BakingDatabase.getsInstance(this);
        AppExecutors.getInstance().getDiskIO().execute(() -> mDatabase.clearAllTables());

        DataBaseUtils dataBaseUtils = new DataBaseUtils(this, mDatabase);
        dataBaseUtils.fillDatabase();

        initRecipeFragment();
    }

    private void initRecipeFragment() {
        recipeFragment = new RecipeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }

    private void initStepFragment(int recipeId) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_RECIPE_ID, recipeId);
        stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_container, stepsFragment)
                .addToBackStack(Constants.FRAGMENT_STEPS)
                .commit();
    }

    private void initDetailStepFragment(int stepId) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_STEPS_ID, stepId);
        stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_container, stepDetailFragment)
                .addToBackStack(Constants.FRAGMENT_STEP_DETAIL)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onRecipeSelected(int recipeId) {
        initStepFragment(recipeId);
    }

    @Override
    public void stepSelected(int stepId) {
        initDetailStepFragment(stepId);
    }
}
