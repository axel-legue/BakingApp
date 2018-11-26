package com.legue.axel.bankingapp.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.adapter.IngredientsAdapter;
import com.legue.axel.bankingapp.adapter.StepAdapter;
import com.legue.axel.bankingapp.database.ViewModel.IngredientViewModel;
import com.legue.axel.bankingapp.database.ViewModel.RecipeViewModel;
import com.legue.axel.bankingapp.database.ViewModel.StepViewModel;
import com.legue.axel.bankingapp.database.model.Ingredient;
import com.legue.axel.bankingapp.database.model.Recipe;
import com.legue.axel.bankingapp.database.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepsFragment extends Fragment {

    private final static String TAG = StepsFragment.class.getName();

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


    private Context mContext;
    /**
     * Ingredients vars
     */
    private List<Ingredient> ingredientList;
    private IngredientViewModel ingredientViewModel;
    private IngredientsAdapter ingredientsAdapter;
    /**
     * Recipe vars
     */
    private RecipeViewModel recipeViewModel;
    private Recipe recipeSelected;
    private int recipeId;
    /**
     * Steps vars
     */
    private StepAdapter stepAdapter;
    private List<Step> stepList;
    private StepViewModel stepViewModel;

    /**
     * Listener
     */

    private StepListener stepListener;

    public interface StepListener {
        void stepSelected(int firstStepId, int lastStepId, int stepSelectedId);
    }


    public StepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        if (getArguments() != null && getArguments().containsKey(Constants.KEY_RECIPE_ID)) {
            recipeId = getArguments().getInt(Constants.KEY_RECIPE_ID);
        } else {
            recipeId = -1;
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            stepListener = (StepListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " Must implement onStepSelected");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.KEY_RECIPE_ID, recipeId);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(Constants.KEY_RECIPE_ID)) {
            recipeId = savedInstanceState.getInt(Constants.KEY_RECIPE_ID);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initData() {

        mContext = getActivity();

        if (ingredientList == null) {
            ingredientList = new ArrayList<>();
        }
        if (stepList == null) {
            stepList = new ArrayList<>();
        }

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getRecipeById(recipeId).observe(this, recipe -> {
            if (recipe != null) {
                recipeSelected = recipe;
                displayRecipeInfo(recipeSelected);
            }
        });

        ingredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        ingredientViewModel.getRecipeIngredients(recipeId).observe(this, ingredients -> {
            if (ingredients != null) {
                // TODO : Add ProgressBar
                ingredientList.clear();
                ingredientList.addAll(ingredients);
                ingredientsAdapter.notifyDataSetChanged();
            }
        });

        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        stepViewModel.getRecipeSteps(recipeId).observe(this, steps -> {
            if (steps != null) {
                // TODO : Add ProgressBar
                stepList.clear();
                stepList.addAll(steps);
                stepAdapter.notifyDataSetChanged();
                setDifficulty(stepList);
            }
        });

        ingredientsAdapter = new IngredientsAdapter(mContext, ingredientList);
        mIngredientsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
        mIngredientsRecyclerView.setAdapter(ingredientsAdapter);
        mIngredientsRecyclerView.setHasFixedSize(true);

        stepAdapter = new StepAdapter(mContext, stepList, stepListener);
        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mStepsRecyclerView.setAdapter(stepAdapter);
        mStepsRecyclerView.setHasFixedSize(true);

    }

    private void displayRecipeInfo(Recipe recipe) {
        switch (recipe.getTitle()) {
            case "Nutella Pie":
                loadImage(R.drawable.nutella_pie);
                break;
            case "Brownies":
                loadImage(R.drawable.brownies);
                break;
            case "Yellow Cake":
                loadImage(R.drawable.yellow_cake);
                break;
            case "Cheesecake":
                loadImage(R.drawable.cheese_cake);
                break;
            default:
                loadImage(R.drawable.placeholder_image);
                break;
        }

        String servingsString = mContext.getString(R.string.servings, recipe.getServings());
        mRecipeServings.setText(servingsString);
        mRecipeTitle.setText(recipe.getTitle());

    }

    private void setDifficulty(List<Step> stepList) {
        if (stepList != null && stepList.size() > 0) {
            if (stepList.size() < 10) {
                mLevelImage1.setVisibility(View.VISIBLE);
                mLevelImage2.setVisibility(View.GONE);
                mLevelImage3.setVisibility(View.GONE);
            } else if (stepList.size() > 12) {
                mLevelImage1.setVisibility(View.VISIBLE);
                mLevelImage2.setVisibility(View.VISIBLE);
                mLevelImage3.setVisibility(View.VISIBLE);
            } else {
                mLevelImage1.setVisibility(View.VISIBLE);
                mLevelImage2.setVisibility(View.VISIBLE);
                mLevelImage3.setVisibility(View.GONE);
            }
        } else {
            mLevelImage1.setVisibility(View.GONE);
            mLevelImage2.setVisibility(View.GONE);
            mLevelImage3.setVisibility(View.GONE);
        }
    }

    private void loadImage(int drawable) {
        Picasso.with(mContext)
                .load(drawable)
                .error(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder_image)
                .into(mRecipeImage);
    }
}