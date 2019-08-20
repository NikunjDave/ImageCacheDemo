package com.weatherapp.model

import com.google.gson.annotations.SerializedName

class Forecast {
    @SerializedName("forecastday")
    val listForecastDay : List<ForecastDay>? = null


}