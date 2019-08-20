package com.hkuaapp.network

import com.test.gojek.Model.Weather
import io.reactivex.Single
import retrofit2.http.GET




interface ApiInterface {
    @GET("/photos")
    fun getAllPhotos(): Single<List<Weather>>


}