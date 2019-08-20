package com.weatherapp.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.android.gms.location.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CurrentLocationManager extends LiveData<Location> {

    private static CurrentLocationManager instance;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    public static CurrentLocationManager getInstance(Context appContext) {
        if (instance == null) {
            instance = new CurrentLocationManager(appContext);
        }
        return instance;
    }

    @SuppressLint("MissingPermission")
    private CurrentLocationManager(final Context appContext) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext);
        createLocationRequest();
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful() && task.getResult() != null) {
                    setValue(task.getResult());
                } else {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                }
            }
        });

    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (location != null)
                    setValue(location);
            }
        }
    };

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

}
