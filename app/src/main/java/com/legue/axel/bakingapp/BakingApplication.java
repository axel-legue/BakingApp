package com.legue.axel.bakingapp;

import android.app.Application;

import com.legue.axel.bakingapp.retrofit.RetrofitManager;

public class BakingApplication extends Application {

    private static BakingApplication instance;
    private RetrofitManager mRetrofitManager;
    private String recipesJson;


    public static BakingApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mRetrofitManager = new RetrofitManager();
    }

    public RetrofitManager getRetrofitManager() {
        return mRetrofitManager;
    }

    public String getRecipesString() {
        return recipesJson;
    }

    public void setRecipesString(String recipesJson) {
        this.recipesJson = recipesJson;
    }
}
