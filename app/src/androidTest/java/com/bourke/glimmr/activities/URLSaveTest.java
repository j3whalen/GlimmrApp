package com.bourke.glimmr.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.bourke.glimmr.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class URLSaveTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void uRLSaveTest() {
        ViewInteraction textView = onView(
allOf(withId(R.id.textNotNow), withText("or, just browse some photos…"), isDisplayed()));
        textView.perform(click());
        
        ViewInteraction actionMenuItemView = onView(
allOf(withId(R.id.menu_search), withContentDescription("Search"), isDisplayed()));
        actionMenuItemView.perform(click());
        
        ViewInteraction searchAutoComplete = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
withParent(allOf(withClassName(is("android.widget.LinearLayout")),
withParent(withClassName(is("android.widget.LinearLayout"))))),
isDisplayed()));
        searchAutoComplete.perform(replaceText("tiger"), closeSoftKeyboard());
        
        ViewInteraction searchAutoComplete2 = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("tiger"),
withParent(allOf(withClassName(is("android.widget.LinearLayout")),
withParent(withClassName(is("android.widget.LinearLayout"))))),
isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());
        
        }

    }
