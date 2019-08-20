package com.weatherapp.viewmodel

import android.util.Log
import com.weatherapp.model.ResponseForecast
import com.weatherapp.model.ResponseLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherViewModel : BaseViewModel<WeatherNavigator>() {

    fun callWeatherApi(city: String) {

        val apiKey = "82659140320e4d15aad123558191408"
        // show Loading screen
        getNavigator()?.showLoading()

        compositeDisposable.add(mApiService.getCurrentWeather(apiKey, city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ResponseLocation>() {
                override fun onSuccess(location: ResponseLocation) {
                    getNavigator()?.onLocationFetched(location)
                    Log.e("tag", "" + location.currentlocation?.name)
                }

                override fun onError(e: Throwable) {
                    Log.e("tag", e.message)
                }
            })
        )
    }


    fun callWeatherForeCastApi(city: String,numOfDays : Int) {

        val apiKey = "82659140320e4d15aad123558191408"
        // show Loading screen
        getNavigator()?.showLoading()

        compositeDisposable.add(mApiService.getForecastWeather(apiKey, city,numOfDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ResponseForecast>() {
                override fun onSuccess(weather: ResponseForecast) {
                    getNavigator()?.onForecastSuccess(weather)
                    Log.e("tag", "" + weather.location?.name)
                }

                override fun onError(e: Throwable) {
                    Log.e("tag", e.message)
                }
            })
        )
    }

}