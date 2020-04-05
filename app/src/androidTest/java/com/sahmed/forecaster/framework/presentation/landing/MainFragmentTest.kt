package com.sahmed.forecaster.framework.presentation.landing

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sahmed.forecaster.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest{


    @Test
    fun checkBothCTAButtonsAreDisplayed(){
        launchFragmentInContainer<MainFragment>()

        onView(withId(R.id.btn_cities_weather)).check(matches(isDisplayed()))
    }
}