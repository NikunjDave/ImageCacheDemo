package com.weatherapp.model
import com.google.gson.annotations.SerializedName

class ResponseLocation {
    @SerializedName("location")
    val currentlocation : CurrentLocation? = null
}