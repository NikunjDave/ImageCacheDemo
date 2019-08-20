package com.weatherapp.viewmodel

import android.util.Log
import com.weatherapp.BuildConfig
import com.weatherapp.model.ResponseForecast
import com.weatherapp.model.ResponseLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherViewModel : BaseViewModel<WeatherNavigator>() {


    fun callWeatherForeCastApi(city: String,numOfDays : Int) {
        getNavigator()?.showLoading()
        compositeDisposable.add(mApiService.getForecastWeather(BuildConfig.API_TOKEN, city,numOfDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ResponseForecast>() {
                override fun onSuccess(weather: ResponseForecast) {
                    getNavigator()?.showWeatherScreen()
                    getNavigator()?.onForecastSuccess(weather)
                }

                override fun onError(e: Throwable) {
                    Logger.e(e.localizedMessage)
                    getNavigator()?.showErrorScreen()
                }
            })
        )
    }

}