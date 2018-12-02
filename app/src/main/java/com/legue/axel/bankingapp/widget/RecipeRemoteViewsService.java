package com.legue.axel.bankingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.model.Recipe;

import java.util.List;

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
    private List<Recipe> mRecipeList;

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
        mRecipeList = BakingDatabase.getsInstance(mContext).recipeDao().getAllRecipeInWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: ");
        return mRecipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.i(TAG, "getViewAt: ");
        Recipe recipe = mRecipeList.get(i);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        views.setTextViewText(R.id.tv_recipe_title, recipe.getTitle());

        String servings = mContext.getResources().getString(R.string.servings, recipe.getServings());
        views.setTextViewText(R.id.tv_recipe_servings, servings);


        switch (recipe.getTitle()) {
            case "Nutella Pie":
                views.setImageViewResource(R.id.iv_recipe, R.drawable.nutella_pie);
                break;
            case "Brownies":

                views.setImageViewResource(R.id.iv_recipe, R.drawable.brownies);
                break;
            case "Yellow Cake":
                views.setImageViewResource(R.id.iv_recipe, R.drawable.yellow_cake);
                break;
            case "Cheesecake":
                views.setImageViewResource(R.id.iv_recipe, R.drawable.cheese_cake);
                break;
            default:
                views.setImageViewResource(R.id.iv_recipe, R.drawable.placeholder_image);
                break;
        }

        // Fill in the onClick PendingIntent Template using the specific RecipeId
        // for each item individually.
        Bundle extras = new Bundle();
        extras.putInt(Constants.KEY_RECIPE_ID, recipe.getRecipeId());
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

