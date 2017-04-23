package com.bourke.glimmr.activities;
/*
*@author Joshua Whalen
*
* my job was issue 4 regaurding the explore tab functionality using expresso
* I wrote tests to check:
* 1.) The explore text is correct and exists
* 2.) The explore tab, when clicked on, takes you to the explore view.
* >>>>I cannot find the issue of the explore tab repeating which leads
* me to believe it may have been resolved so these tests SHOULD pass.
* Documentation
* - in build.gradle, under dependecies paste the following code:
*
* androidTestCompile('com.android.support.test.espresso:espresso-core:2.2') {
    // Necessary if your app targets Marshmallow (since Espresso
    // hasn't moved to Marshmallow yet)
    exclude group: 'com.android.support', module: 'support-annotations'
  }
  androidTestCompile('com.android.support.test:runner:0.3') {
    // Necessary if your app targets Marshmallow (since the test runner
    // hasn't moved to Marshmallow yet)
    exclude group: 'com.android.support', module: 'support-annotations'
  }

  -then copy and paste the following code in build.gradle, under defaultConfig:

      testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

 after following these steps you will be able to run expresso.
 If you have issues refer to the link: https://google.github.io/android-testing-support-library/docs/espresso/setup/
 */

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bourke.glimmr.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestExploreFunctionality {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testExploreFunctionality() {
    //Test to check the text is equal to "Explore" and exists
        ViewInteraction textView = onView(
                allOf(withText("EXPLORE"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.md__menu),
                                        0),
                                5),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    //Tests to check that when the explore tab is clicked it brings you to viewPager activity
        //This test should have failed because the issue was that this tab keeps repeating
        //However this issue must have been resolved because when explore is clicked it brings you
        // to the correct activity.
        ViewInteraction textView3 = onView(
                allOf(withText("EXPLORE"), isDisplayed()));
        textView3.perform(click());

        ViewInteraction viewPager = onView(
                allOf(withId(R.id.viewPager), isDisplayed()));
        viewPager.perform(swipeLeft());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
