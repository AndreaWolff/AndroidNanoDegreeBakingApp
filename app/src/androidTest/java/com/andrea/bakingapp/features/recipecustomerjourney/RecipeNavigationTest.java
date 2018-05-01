package com.andrea.bakingapp.features.recipecustomerjourney;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.andrea.bakingapp.R;
import com.andrea.bakingapp.features.details.ui.DetailsActivity;
import com.andrea.bakingapp.features.main.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<DetailsActivity> detailsActivityTestRule = new ActivityTestRule<>(DetailsActivity.class);

    @Test
    public void recipeMenu_navigateTo_recipeInstructions() throws InterruptedException {
        // Waits while loading spinner is on the screen
        Thread.sleep(1000);

        // Verify Recipe Menu contains recipes
        onView(withText("Yellow Cake")).check(matches(isDisplayed())).perform(click());

        // Verify Recipe Ingredients and Steps are displayed
        Thread.sleep(500);
        onView(withText("Yellow Cake")).check(matches(withText("Yellow Cake")));
        onView(withText("salt")).check(matches(isDisplayed()));
        onView(withText("1.5")).check(matches(isDisplayed()));
        onView(first(withText("TSP"))).check(matches(isDisplayed()));

        onView(withText("Recipe Introduction")).check(matches(isDisplayed())).perform(click());

        // Verify Recipe Instruction is loaded and displays information
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));
        onView(withText("This recipe will teach you how to bake a Yellow Cake.")).check(matches(isDisplayed()));

        // Verify Recipe Instruction action buttons navigate through the steps
        onView(withId(R.id.nextButton)).check(matches(isDisplayed()));
        onView(withId(R.id.previousButton)).check(matches(not(isDisplayed())));
        onView(withId(R.id.nextButton)).perform(click()).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.previousButton)).perform(click());

        onView(withId(R.id.nextButton)).check(matches(isDisplayed()));
        onView(withId(R.id.previousButton)).check(matches(isDisplayed()));
    }

    // Taken from https://stackoverflow.com/questions/29378552/in-espresso-how-to-choose-one-the-view-who-have-same-id-to-avoid-ambiguousviewm
    public static Matcher<View> first(Matcher<View> expected) {

        return new TypeSafeMatcher<View>() {
            private boolean first = false;

            @Override
            protected boolean matchesSafely(View item) {
                if (expected.matches(item) && !first) {
                    return first = true;
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Matcher.first( " + expected.toString() + " )");
            }
        };
    }
}
