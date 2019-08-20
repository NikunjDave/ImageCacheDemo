package com.weatherapp.viewmodel

import com.weatherapp.model.ResponseForecast

interface WeatherNavigator : BaseNavigator {
    fun showWeatherScreen()
    fun onForecastSuccess(forecast: ResponseForecast)
}