package com.legue.axel.bankingapp;


import com.legue.axel.bankingapp.activity.RecipeActivity;
import com.legue.axel.bankingapp.activity.StepsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class RecipeActivityScreenTest {

    private static final String NUTELLE_PIE = "Nutella Pie";


    private final static int RECIPE_ID_SELECTED = 1;
    private final static String KEY_RECIPE_ID = "id";

    @Rule
    public IntentsTestRule<RecipeActivity> mActivityRule = new IntentsTestRule<>(
            RecipeActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensStepsActivity() {

        /**
         * Click RecyclerView Item
         */
        onView(ViewMatchers.withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        /**
         * Test Intent sent
         */
        intended(allOf(
                hasExtra(KEY_RECIPE_ID, RECIPE_ID_SELECTED),
                hasComponent(StepsActivity.class.getName()),
                isInternal()
        ));

        /**
         * Test recipe title displayed on new Activity
         */
        onView(withId(R.id.tv_recipe_name)).check(matches(withText(NUTELLE_PIE)));
    }


}
