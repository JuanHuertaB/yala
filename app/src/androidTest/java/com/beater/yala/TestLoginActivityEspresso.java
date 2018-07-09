package com.beater.yala;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by JuanCarlos on 6/07/2018.
 */
@RunWith(AndroidJUnit4.class)
public class TestLoginActivityEspresso {

    @Rule
    public ActivityTestRule<LoginActivity> mActivitiesRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ensureLogIn(){
        onView(withId(R.id.username))
                .perform(typeText("Juan carlos"));
        onView(withId(R.id.password))
                .perform(typeText("Qwe"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.login_btn))
                .perform(click());

    }
}
