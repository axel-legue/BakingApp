package com.legue.axel.bankingapp;


import com.legue.axel.bankingapp.activity.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    public static final String NUTELLE_PIE = "Nutella Pie";

    @Rule
    public ActivityTestRule<RecipeActivity> mRecipeActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {

        onView(ViewMatchers.withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_recipe_name)).check(matches(withText(NUTELLE_PIE)));
    }


}
