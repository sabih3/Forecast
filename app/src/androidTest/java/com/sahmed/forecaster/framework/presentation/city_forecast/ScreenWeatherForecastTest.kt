package com.sahmed.forecaster.framework.presentation.city_forecast

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.presentation.landing.MainActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenWeatherForecastTest{

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java,false,false)

    @Test
    fun checkIfListViewIsDisplayed(){
        activityRule.launchActivity(null)

        onView(withId(R.id.btn_current_forecast)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_current_forecast)).perform(ViewActions.click())

        onView(withId(R.id.rv_forecast)).check(matches(isDisplayed()))

    }
}