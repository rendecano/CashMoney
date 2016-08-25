package com.rendecano.cashmoney;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rendecano.cashmoney.presentation.activity.CashMoneyActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Ren Decano.
 */

@RunWith(AndroidJUnit4.class)
public class CashMoneyActivityTest {

    @Rule
    public ActivityTestRule<CashMoneyActivity> mActivityRule =
            new ActivityTestRule<>(CashMoneyActivity.class);

    @Test
    public void testInitialValues() {

        SystemClock.sleep(2000);

        // Check if views were displayed
        onView(withId(R.id.txtBase)).check(matches(isDisplayed()));
        onView(withId(R.id.viewpager)).check(matches(isDisplayed()));
        onView(withId(R.id.txtRate)).check(matches(isDisplayed()));

        onView(withId(R.id.txtBase)).check(matches(withText("AUD")));
        onView(withId(R.id.etConvert)).check(matches(withText("$1")));
        onView(withId(R.id.txtRate)).check(matches(withText("CA$0.98")));
    }

    @Test
    public void testVerifyConvertedRate() throws Exception {

        SystemClock.sleep(4000);

        // Type 100 as value
        onView(withId(R.id.etConvert)).perform(typeText("100"),
                closeSoftKeyboard());

        // Swipe viewpager
        onView(withId(R.id.viewpager)).perform(swipeLeft());
        SystemClock.sleep(1000);

        // Verify the converted rate if symbol changes and there's a value

        onView(withId(R.id.txtRate)).check(matches(withText("$761.54")));
    }
}
