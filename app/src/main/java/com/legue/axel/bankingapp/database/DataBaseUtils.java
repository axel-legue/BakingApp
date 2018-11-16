package com.legue.axel.bankingapp.database;

import android.content.Context;
import android.util.Log;

import com.legue.axel.bankingapp.AppExecutors;
import com.legue.axel.bankingapp.Constants;
import com.legue.axel.bankingapp.database.model.Ingredient;
import com.legue.axel.bankingapp.database.model.Recipe;
import com.legue.axel.bankingapp.database.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    private final static String TAG = DataBaseUtils.class.getName();
    private Context mContext;
    private BakingDatabase mDatabase;
    private List<Recipe> recipes;
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public DataBaseUtils(Context context, BakingDatabase database) {
        this.mContext = context;
        this.mDatabase = database;
    }

    public void fillDatabase() {
        recipes = new ArrayList<>();
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        String jsonString;
        try {
            InputStream inputStream = mContext.getAssets().open("baking.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");


            JSONArray jsonArray = new JSONArray(jsonString);
            if (jsonArray.length() > 0) {
                insertRecipes(jsonArray);
            }

            AppExecutors.getInstance().getDiskIO().execute(() -> {
                mDatabase.recipeDao().insertAllRecipe(recipes);
                mDatabase.ingredientDao().insertAllIngredient(ingredients);
                mDatabase.stepDao().insertAllStep(steps);
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


    private void insertRecipes(JSONArray jsonArray) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                recipes.add(createRecipe(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void insertIngredients(JSONArray jsonArray, int recipeId) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                ingredients.add(createIngredient(jsonArray.getJSONObject(i), recipeId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void insertSteps(JSONArray jsonArray, int recipeId) {

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                steps.add(createStep(jsonArray.getJSONObject(i), recipeId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private Recipe createRecipe(JSONObject jsonObject) {

        int id = -1;
        String name = null;
        int servings = -1;
        String image = null;

        try {

            if (jsonObject.has(Constants.KEY_RECIPE_ID)) {
                id = jsonObject.getInt(Constants.KEY_RECIPE_ID);
            }
            if (jsonObject.has(Constants.KEY_RECIPE_NAME)) {
                name = jsonObject.getString(Constants.KEY_RECIPE_NAME);
            }
            if (jsonObject.has(Constants.KEY_RECIPE_SERVINGS)) {
                servings = jsonObject.getInt(Constants.KEY_RECIPE_SERVINGS);
            }
            if (jsonObject.has(Constants.KEY_RECIPE_IMAGE)) {
                image = jsonObject.getString(Constants.KEY_RECIPE_IMAGE);
            }
            if (jsonObject.has(Constants.KEY_RECIPE_INGREDIENTS)) {
                if (jsonObject.getJSONArray(Constants.KEY_RECIPE_INGREDIENTS).length() > 0 && id != -1) {
                    insertIngredients(jsonObject.getJSONArray(Constants.KEY_RECIPE_INGREDIENTS), id);
                }
            }
            if (jsonObject.has(Constants.KEY_RECIPE_STEPS)) {
                if (jsonObject.getJSONArray(Constants.KEY_RECIPE_STEPS).length() > 0 && id != -1) {
                    insertSteps(jsonObject.getJSONArray(Constants.KEY_RECIPE_STEPS), id);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Recipe(id, name, servings, image);
    }

    private Ingredient createIngredient(JSONObject jsonObject, int recipeId) {
        int id = 0;
        double quantity = -1;
        String measure = null;
        String name = null;


        try {
            if (jsonObject.has(Constants.KEY_QUANTITY)) {
                quantity = jsonObject.getDouble(Constants.KEY_QUANTITY);
            }
            if (jsonObject.has(Constants.KEY_MEASURE)) {
                measure = jsonObject.getString(Constants.KEY_MEASURE);
            }
            if (jsonObject.has(Constants.KEY_INGREDIENT_NAME)) {
                name = jsonObject.getString(Constants.KEY_INGREDIENT_NAME);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Ingredient(id, quantity, measure, name, recipeId);
    }

    private Step createStep(JSONObject jsonObject, int recipeId) {
        int id = 0;
        String shortDescription = null;
        String description = null;
        String videoURL = null;
        String thumbnailURL = null;

        try {
            if (jsonObject.has(Constants.KEY_STEPS_SHORT_DESCRIPTION)) {
                shortDescription = jsonObject.getString(Constants.KEY_STEPS_SHORT_DESCRIPTION);
            }
            if (jsonObject.has(Constants.KEY_STEPS_DESCRIPTION)) {
                description = jsonObject.getString(Constants.KEY_STEPS_DESCRIPTION);
            }
            if (jsonObject.has(Constants.KEY_STEPS_VIDEO_URL)) {
                videoURL = jsonObject.getString(Constants.KEY_STEPS_VIDEO_URL);
            }
            if (jsonObject.has(Constants.KEY_STEPS_THUMBNAIL_URL)) {
                thumbnailURL = jsonObject.getString(Constants.KEY_STEPS_THUMBNAIL_URL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Step(id, shortDescription, description, videoURL, thumbnailURL, recipeId);
    }
}
