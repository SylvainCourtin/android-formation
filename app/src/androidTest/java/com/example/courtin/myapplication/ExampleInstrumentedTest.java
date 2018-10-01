package com.example.courtin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.courtin.myapplication.activity.LoginActivity;
import com.example.courtin.myapplication.utils.Constantes;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void clearMemoryBefore() {
        SharedPreferences.Editor editor =  mActivityRule.getActivity().getSharedPreferences(Constantes.SECRET, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void failLogin() {
        Espresso.onView(withId(R.id.buttonConnexion)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(withId(R.id.failConnexion)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void workLogin() {
        Espresso.onView(withId(R.id.username)).perform(typeText("admin"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.password)).perform(typeText("admin"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.buttonConnexion)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(withId(R.id.failConnexion)).check(doesNotExist());
    }


}
