package algonquin.cst2335.myapplicatio116;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPassword));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginbutton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testFindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPassword));
        appCompatEditText.perform(replaceText("12345q@"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginbutton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPassword));
        appCompatEditText.perform(replaceText("12345Q@"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginbutton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testFindMissingSpecial() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPassword));
        appCompatEditText.perform(replaceText("12345Qq"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginbutton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testFindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPassword));
        appCompatEditText.perform(replaceText("Qq@lsjdflsdfj"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginbutton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("You shall not pass")));
    }

    @Test
    public void testFindAll() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editPassword));
        appCompatEditText.perform(replaceText("12345Qq@"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(withId(R.id.loginbutton));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textview));
        textView.check(matches(withText("Your password meets the requirements")));
    }
}
