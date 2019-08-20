package com.test.gojek.viewmodel

import com.test.gojek.Model.ResponseLocation

interface WeatherNavigator : BaseNavigtor {

    fun onLocationFetched(location: ResponseLocation)
    fun onLocationError()
}