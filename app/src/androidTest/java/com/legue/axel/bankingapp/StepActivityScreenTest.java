package com.legue.axel.bankingapp;

import android.content.Intent;

import com.legue.axel.bankingapp.activity.StepsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class StepActivityScreenTest {


    private final static int RECIPE_ID_SELECTED = 1;
    private final static String KEY_RECIPE_ID = "id";

    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<StepsActivity> mStepsActivityTestRule =
            new ActivityTestRule<StepsActivity>(StepsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_RECIPE_ID, RECIPE_ID_SELECTED);
                    return intent;
                }
            };

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mStepsActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }


    @Test
    public void clickRecyclerViewItem_OpensDetailsActivity() {

        onView(withId(R.id.fragment_steps_view))
                .perform(swipeUp());

        onView(withId(R.id.rv_description))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detail_container)).check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
