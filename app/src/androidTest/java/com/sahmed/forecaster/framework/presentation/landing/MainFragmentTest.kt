package com.sahmed.forecaster.framework.presentation.landing

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sahmed.forecaster.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest{

    @get:Rule
    val activityRule =  ActivityTestRule(MainActivity::class.java,false,false)

    @Test
    fun checkBothCTAButtonsAreDisplayed(){
        activityRule.launchActivity(null)

        onView(withId(R.id.btn_cities_weather)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_current_forecast)).check(matches(isDisplayed()))
    }

}