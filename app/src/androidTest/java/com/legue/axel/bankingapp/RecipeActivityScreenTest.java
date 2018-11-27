package com.legue.axel.bankingapp;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.legue.axel.bankingapp.activity.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    public static final String NUTELLE_PIE = "Nutella Pie";

    @Rule
    public ActivityTestRule<RecipeActivity> mRecipeActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickOnRecyclerViewItem_OpenStepsActivity() {
        // First Scroll to the position that needs to be matched ad click on it
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_recipe_name)).check(matches(withText(NUTELLE_PIE)));
    }

}
