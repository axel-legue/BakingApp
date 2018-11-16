package com.legue.axel.bankingapp;

import java.util.HashMap;
import java.util.Map;

public class Constants {

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
    public static final String KEY_STEPS_SHORT_DESCRIPTION = "shortDescription";
    public static final String KEY_STEPS_DESCRIPTION = "description";
    public static final String KEY_STEPS_VIDEO_URL = "videoURL";
    public static final String KEY_STEPS_THUMBNAIL_URL = "thumbnailURL";

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
}
