package com.legue.axel.bankingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;

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

    private RecipeFragment recipeFragment;
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


}
