package com.test.gojek.ui

import android.util.Log
import com.test.gojek.Model.ResponseLocation
import com.test.gojek.viewmodel.BaseViewModel
import com.test.gojek.viewmodel.WeatherNavigator
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
                    Log.e("tag", "" + location.location?.name)
                }

                override fun onError(e: Throwable) {
                    Log.e("tag", e.message)
                }
            })
        )
    }

}