package com.legue.axel.bakingapp;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    /**
     * KEYS SharedPreferences
     */
    public static final String KEY_FIRST_RUN = "first_run";
    public static final String KEY_FAVORITE_RECIPE = "favorite_recipe";

    /**
     * KEYS Recipe
     */
    public static final String KEY_RECIPE_ID = "id";
    public static final String KEY_RECIPE_NAME = "name";
    public static final String KEY_RECIPE_SERVINGS = "servings";
    public static final String KEY_RECIPE_IMAGE = "image";
    public static final String KEY_RECIPE_INGREDIENTS = "ingredients";
    public static final String KEY_RECIPE_STEPS = "steps";

    /**
     * KEYS Ingredients
     */
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_MEASURE = "measure";
    public static final String KEY_INGREDIENT_NAME = "ingredient";

    /**
     * KEYS Steps
     */
    public static final String KEY_STEPS_ID = "id";
    public static final String KEY_FIRST_STEP_ID = "first_id";
    public static final String KEY_LAST_STEP_ID = "last_id";
    public static final String KEY_STEPS_SHORT_DESCRIPTION = "shortDescription";
    public static final String KEY_STEPS_DESCRIPTION = "description";
    public static final String KEY_STEPS_VIDEO_URL = "videoURL";
    public static final String KEY_STEPS_THUMBNAIL_URL = "thumbnailURL";
    public static final String KEY_STEPS_EXO_CURRENT_TIME = "currentTime";
    public static final String KEY_STEPS_EXO_CURRENT_WINDOW = "currentWindow";
    public static final String KEY_STEPS_EXO_IS_READY = "isReady";
    public static final String KEY_STEPS_EXO_VIDEO_URL = "videoURL";

    public static final String ERROR = "Error";
    public static final int ACTION_ERROR = 200;
    public static final int ACTION_COMPLETE = 201;
    public static final int ACTION_GET_RECIPE = 202;

    /**
     * Keys for measure
     */
    public static final Map<String, String> measureMap;

    static {
        measureMap = new HashMap<>();
        measureMap.put("CUP", "cup(s)");
        measureMap.put("TBLSP", "tablespoon(s)");
        measureMap.put("TSP", "teaspoon(s)");
        measureMap.put("K", "Kilo(s)");
        measureMap.put("G", "gram(s)");
        measureMap.put("OZ", "oz(s)");
        measureMap.put("UNIT", "unit");
    }

    /**
     * Actions
     */
    public static String ACTION_UPDATE_WIDGET = "android.appwidget.action.APPWIDGET_UPDATE";

}
