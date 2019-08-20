package com.weatherapp.viewmodel

import com.weatherapp.model.ResponseForecast
import com.weatherapp.model.ResponseLocation

interface WeatherNavigator : BaseNavigtor {

    fun onLocationFetched(location: ResponseLocation)

    fun onForecastSuccess(forecast: ResponseForecast)
    fun onLocationError()
}