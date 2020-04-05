package com.sahmed.forecaster.framework.presentation.cities_weather

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sahmed.forecaster.framework.presentation.landing.MainFragment
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.presentation.landing.MainActivity
import org.hamcrest.core.IsNot.not
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenCityWeathersListTest{

    @get:Rule
    val activityRule =  ActivityTestRule(MainActivity::class.java,false,false)


    @Test
    fun checkIfSearchFieldIsInputReady(){
        activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.btn_cities_weather)).perform(ViewActions.click())
        onView(withId(R.id.search_field)).check(matches(isDisplayed()))
        onView(withId(R.id.search_field)).check(matches(isEnabled()))

    }

    @Test
    fun checkIfProgressIsNotVisibleInitially(){
        activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.btn_cities_weather)).perform(ViewActions.click())
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())))
    }


}