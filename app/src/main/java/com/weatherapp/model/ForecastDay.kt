package com.weatherapp.model

import com.google.gson.annotations.SerializedName

class ForecastDay {
    @SerializedName("day")
    val day: Day? = null

    @SerializedName("date")
    val date : String ? = null

    @SerializedName("date_epoch")
    val date_epoch : Long = 0
}