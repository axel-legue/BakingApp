package com.legue.axel.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.legue.axel.bakingapp.Constants;
import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.database.BakingDatabase;
import com.legue.axel.bakingapp.database.model.Ingredient;
import com.legue.axel.bakingapp.database.model.Recipe;

import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecipeRemoteViewsService extends RemoteViewsService {

    private final String TAG = RecipeRemoteViewsService.class.getName();


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i(TAG, "onGetViewFactory: ");
        return new RecipeRemoteViewFactory(this.getApplicationContext());
    }
}

class RecipeRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final String TAG = RecipeRemoteViewFactory.class.getName();

    private Context mContext;
    private List<Ingredient> mIngredientList;
    private Recipe mRecipe;

    public RecipeRemoteViewFactory(Context context) {
        Log.i(TAG, "RecipeRemoteViewFactory: ");
        mContext = context;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onDataSetChanged() {
        Log.i(TAG, "onDataSetChanged: ");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("com.legue.axel.bankingapp", MODE_PRIVATE);
        if (sharedPreferences.contains(Constants.KEY_FAVORITE_RECIPE)) {
            String json = sharedPreferences.getString(Constants.KEY_FAVORITE_RECIPE, "");
            mRecipe = gson.fromJson(json, Recipe.class);
            mIngredientList = BakingDatabase.getsInstance(mContext).ingredientDao().getRecipeIngredientsWidget(mRecipe.getRecipeId());
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: ");
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.i(TAG, "getViewAt: ");
        Ingredient ingredient = mIngredientList.get(i);
        String measure = null;
        String quantity;
        String name;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_item);

        if (ingredient.getMeasure() != null && ingredient.getMeasure().length() > 0) {
            if (Constants.measureMap.containsKey(ingredient.getMeasure())) {
                measure = Constants.measureMap.get(ingredient.getMeasure());
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        quantity = decimalFormat.format(ingredient.getQuantity());
        if (ingredient.getName() != null && ingredient.getName().length() > 0) {
            name = ingredient.getName().toLowerCase();
        } else {
            name = "unknown";
        }

        String ingredientText = mContext.getString(R.string.ingredient_quantity_widget, quantity, measure, name);

        views.setTextViewText(R.id.tv_ingredient_name, ingredientText);

        // Fill in the onClick PendingIntent Template using the specific RecipeId
        // for each item individually.
        Bundle extras = new Bundle();
        extras.putInt(Constants.KEY_RECIPE_ID, mRecipe.getRecipeId());
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.container_recipe_widget, fillIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}

