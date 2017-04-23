package com.bourke.glimmr.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.bourke.glimmr.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityTestRule<ExploreActivity> mActivityTestRule = new ActivityTestRule<>(ExploreActivity.class);

    @Test
    public void searchTest() {
        ViewInteraction button = onView(
                allOf(withId(R.id.btnLogin), withText("LOGIN TO FLICKR"), isDisplayed()));
        button.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.menu_search), withContentDescription("Search"), isDisplayed()));
        actionMenuItemView.perform(click());

    }

}
