package com.sahmed.forecaster.framework.presentation.cities_weather

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sahmed.forecaster.framework.presentation.landing.MainFragment
import com.sahmed.forecaster.R
import com.sahmed.forecaster.framework.presentation.landing.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenCityWeathersListTest{


    @Test
    fun checkIfSearchFieldIsInputReady(){
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.rv_city_weathers)).check(matches(isDisplayed()))
    }

    @Test
    fun checkIfProgressIsNotVisibleInitially(){

    }

    @Test
    fun checkCityNamesShowValidationError(){

    }

}