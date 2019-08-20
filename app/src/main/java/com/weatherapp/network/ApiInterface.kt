package com.weatherapp.network

import com.weatherapp.model.Weather
import io.reactivex.Single
import retrofit2.http.GET




interface ApiInterface {
    @GET("/photos")
    fun getAllPhotos(): Single<List<Weather>>


}