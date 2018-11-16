package com.legue.axel.bankingapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.legue.axel.bankingapp.adapter.IngredientsAdapter;
import com.legue.axel.bankingapp.database.ViewModel.IngredientViewModel;
import com.legue.axel.bankingapp.database.dao.IngredientDao;
import com.legue.axel.bankingapp.database.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.iv_recipe)
    ImageView mRecipeImage;
    @BindView(R.id.tv_recipe_name)
    TextView mRecipeTitle;
    @BindView(R.id.tv_servings)
    TextView mRecipeServings;
    @BindView(R.id.iv_level_1)
    ImageView mLevelImage1;
    @BindView(R.id.iv_level_2)
    ImageView mLevelImage2;
    @BindView(R.id.iv_level_3)
    ImageView mLevelImage3;
    @BindView(R.id.rv_ingredients)
    RecyclerView mIngredientsRecyclerView;
    @BindView(R.id.rv_steps_description)
    RecyclerView mStepsRecyclerView;

    private int recipeId;
    private Context mContext;
    private List<Ingredient> ingredientList;
    private IngredientViewModel ingredientViewModel;

    private IngredientsAdapter ingredientsAdapter;


    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        if (getArguments() != null && getArguments().containsKey(Constants.KEY_RECIPE_ID)) {
            recipeId = getArguments().getInt(Constants.KEY_RECIPE_ID);
        }else{
            recipeId = 1;
        }
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
        if (ingredientList == null) {
            ingredientList = new ArrayList<>();
        }

        ingredientsAdapter = new IngredientsAdapter(mContext, ingredientList);
        mIngredientsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);
        mIngredientsRecyclerView.setHasFixedSize(true);

        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        ingredientViewModel.getAllIngredientList().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                if(ingredients != null){
                    ingredientList.clear();
                    ingredientList.addAll(ingredients);
                    ingredientsAdapter.notifyDataSetChanged();
                }
            }
        });


    }
}
