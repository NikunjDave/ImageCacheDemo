package com.weatherapp.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class CurrentLocationManager private constructor(appContext: Context) : LiveData<Location>() {
    private val mFusedLocationClient: FusedLocationProviderClient
    private var mLocationRequest: LocationRequest? = null

    internal var mLocationCallback: LocationCallback? = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.getLocations()) {
                if (location != null)
                    setValue(location)
            }
        }
    }

    init {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)
        createLocationRequest()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (appContext.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                initLocation()
            }
        } else {
            initLocation()
        }

    }


    @SuppressLint("MissingPermission")
    fun initLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener(object : OnCompleteListener<Location> {
            @SuppressLint("MissingPermission")
            override fun onComplete(task: Task<Location>) {
                if (task.isSuccessful() && task.getResult() != null) {
                    setValue(task.getResult())
                } else {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
                }
            }
        })

    }

    private fun createLocationRequest() {
        mLocationRequest =
            LocationRequest()
        mLocationRequest?.interval = 10000
        mLocationRequest?.fastestInterval = 5000
        mLocationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    protected override fun onInactive() {
        super.onInactive()
        if (mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    companion object {

        private var instance: CurrentLocationManager? = null

        fun getInstance(appContext: Context): CurrentLocationManager {
            if (instance == null) {
                instance = CurrentLocationManager(appContext)
            }
            return instance!!
        }
    }

}
