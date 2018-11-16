package com.legue.axel.bankingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.legue.axel.bankingapp.adapter.RecipeAdapter;
import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.ViewModel.RecipeViewModel;
import com.legue.axel.bankingapp.database.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeFragment extends Fragment {

    private final static String TAG = RecipeFragment.class.getName();

    private Unbinder unbinder;
    @BindView(R.id.rv_recipe)
    RecyclerView mRecipeRecyclerView;


    private BakingDatabase mDatabase;
    private List<Recipe> recipeList;
    private RecipeViewModel recipeViewModel;
    private RecipeAdapter recipeAdapter;
    private Context mContext;

    RecipeAdapter.RecipeListener recipeListener = new RecipeAdapter.RecipeListener() {
        @Override
        public void recipeSelected(Recipe recipe) {
            Log.i(TAG, "recipeSelected: " + recipe.getTitle());
        }
    };

    public RecipeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mContext = getActivity();
        mDatabase = BakingDatabase.getsInstance(mContext);

        if (recipeList == null) {
            recipeList = new ArrayList<>();
        }
        recipeAdapter = new RecipeAdapter(mContext, recipeList, recipeListener);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
