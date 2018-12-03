package com.legue.axel.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.legue.axel.bakingapp.Constants;
import com.legue.axel.bakingapp.R;
import com.legue.axel.bakingapp.activity.StepsActivity;
import com.legue.axel.bakingapp.database.model.Recipe;

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
//        appWidgetManager.updateAppWidget(appWidgetId, null);
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

            switch (mRecipe.getTitle()) {
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
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive ");
        String action = intent.getAction();
        if (action != null && action.equalsIgnoreCase(Constants.ACTION_UPDATE_WIDGET)) {
            int ids[] = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            Log.i(TAG, "action Update ");
            onUpdate(context, AppWidgetManager.getInstance(context), ids);
        }
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

