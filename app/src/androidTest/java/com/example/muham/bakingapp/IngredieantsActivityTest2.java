package com.example.muham.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IngredieantsActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ingredieantsActivityTest2() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipesRecyclerView),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ingreadientsButton), withText("Ingreadients"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.containerr),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.ingredientsRecyclerView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.ingredientTextView), withText("Bittersweet chocolate (60-70% cacao)"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredientsRecyclerView),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Bittersweet chocolate (60-70% cacao)")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.measureTextView), withText("G"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredientsRecyclerView),
                                        0),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("G")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.quantityTextView), withText("350"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredientsRecyclerView),
                                        0),
                                2),
                        isDisplayed()));
        textView3.check(matches(withText("350")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.quantityTextView), withText("350"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.ingredientsRecyclerView),
                                        0),
                                2),
                        isDisplayed()));
        textView4.check(matches(withText("350")));

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
