package com.legue.axel.bankingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.widget.RemoteViews;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.R;
import com.legue.axel.bankingapp.activity.StepsActivity;
import com.legue.axel.bankingapp.database.BakingDatabase;
import com.legue.axel.bankingapp.database.model.Recipe;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private final static String TAG = RecipeWidgetProvider.class.getName();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = getRecipeListRemoteView(context, appWidgetManager, appWidgetId);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_ringredients_widget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static RemoteViews getRecipeListRemoteView(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        SharedPreferences preferences = context.getSharedPreferences("com.legue.axel.bankingapp", MODE_PRIVATE);
        if (preferences.contains(Constants.KEY_FAVORITE_RECIPE)) {
            Gson gson = new Gson();
            String json = preferences.getString(Constants.KEY_FAVORITE_RECIPE, "");
            Recipe mRecipe = gson.fromJson(json, Recipe.class);
            views.setTextViewText(R.id.tv_recipe_name, mRecipe.getTitle());
        }

        Intent intent = new Intent(context, RecipeRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.lv_ringredients_widget, intent);

        Intent appIntent = new Intent(context, StepsActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_ringredients_widget, appPendingIntent);

        views.setEmptyView(R.id.lv_ringredients_widget, R.id.tv_empty_view);

        return views;
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

