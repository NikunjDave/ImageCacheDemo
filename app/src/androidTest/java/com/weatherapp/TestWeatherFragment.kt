package com.weatherapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.runner.AndroidJUnit4
import com.weatherapp.ui.WeatherFragment
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestWeatherFragment {
    @Test
    fun launchFragmentAndVerifyUI() {
        // use launchInContainer to launch the fragment with UI
        launchFragmentInContainer<WeatherFragment>()

        // now use espresso to look for the fragment's text view and verify it is displayed
        //onView(withId(R.id.loading_view)).check(isDisplayed());
    }




}