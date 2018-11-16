package com.legue.axel.bankingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.DataBaseUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private BakingDatabase mDatabase;

    private RecipeFragment recipeFragment;
    private StepsFragment stepsFragment;
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

//        initRecipeFragment();
        initStepFragment();
    }

    private void initRecipeFragment() {
        recipeFragment = new RecipeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
    }

    private void initStepFragment(){
        stepsFragment = new StepsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, stepsFragment)
                .commit();

    }


}
