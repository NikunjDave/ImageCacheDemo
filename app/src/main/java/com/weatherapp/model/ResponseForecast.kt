package com.weatherapp.model

import com.google.gson.annotations.SerializedName

class ResponseForecast {
    @SerializedName("location")
    val location : CurrentLocation? = null
    @SerializedName("current")
    val currentTemperature : CurrentTemperature? = null
    @SerializedName("forecast")
    val forecast : Forecast? =null

}