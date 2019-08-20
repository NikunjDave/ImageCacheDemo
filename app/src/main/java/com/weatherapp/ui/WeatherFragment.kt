package com.weatherapp.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.material.snackbar.Snackbar
import com.weatherapp.R
import com.weatherapp.model.ResponseForecast
import com.weatherapp.model.ResponseLocation
import com.weatherapp.model.Weather
import com.weatherapp.service.CurrentLocationManager
import com.weatherapp.service.FetchAddressIntentService
import com.weatherapp.u.Constants
import com.weatherapp.utils.GpsUtils
import com.weatherapp.viewmodel.WeatherNavigator
import com.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.layout_bottomsheet_temperature.*

class WeatherFragment : Fragment(), WeatherNavigator {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    private val ADDRESS_REQUESTED_KEY = "address-request-pending"
    private val LOCATION_ADDRESS_KEY = "location-address"


    /**
     * Provides access to the Fused Location Provider API.
     */
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var isGPS: Boolean = false

    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationCallback: LocationCallback
    /**
     * Represents a geographical location.
     */
    private var lastLocation: Location? = null


    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private lateinit var resultReceiver: AddressResultReceiver


    override fun onForecastSuccess(responseForecast: ResponseForecast) {
        Logger.d("Forecast current weather " + responseForecast.currentTemperature?.temp_c)
        Logger.d("Forecast day weather " + responseForecast.forecast?.listForecastDay?.get(0)?.day?.avgtemp_c)

        setAdapter()
    }

    override fun onLocationFetched(location: ResponseLocation) {
        //Logger.d("Weather api success")
        setAdapter()

    }

    override fun onLocationError() {
//            Logger.d("Weather api failed")
    }


    override fun showLoading() {
        Logger.d("Weather showloding")
    }

    private lateinit var mCity: String

    private lateinit var mViewModel: WeatherViewModel

    companion object {
        private const val KEY_CITY = "key-city"
        fun newInstance(city: String): Fragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            Logger.d("Weather Fragment instantiated")
            args.putString(KEY_CITY, city)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCity = arguments?.getString(KEY_CITY) ?: "Banglore"
        mViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        mViewModel.setNavigator(this)

        initObjects()
        initLocation()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadData() {
        if (!TextUtils.isEmpty(mCity)) {
            Logger.d("Weather api calling")
            mViewModel.callWeatherForeCastApi(mCity, 5)
        }
    }

    private fun setAdapter() {
        val listWeather = arrayListOf<Weather>()
        listWeather.add(Weather("24", "monday"))
        listWeather.add(Weather("24", "tuesday"))
        listWeather.add(Weather("24", "wednesday"))
        listWeather.add(Weather("24", "thursday"))

        val adapter = WeatherAdapter(listWeather)
        rcvWeather.layoutManager = LinearLayoutManager(context)
        rcvWeather.adapter = adapter
    }


    private fun initObjects() {

        resultReceiver = AddressResultReceiver(Handler())


        GpsUtils(context).turnGPSOn(object : GpsUtils.onGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                // turn on GPS
                isGPS = isGPSEnable
            }
        })


    }

    private fun getLocationUpdates() {

        CurrentLocationManager.getInstance(context).observe(this, object : Observer<Location> {
            override fun onChanged(t: Location?) {
                Logger.d("Location latitude observer :" + t?.latitude)
                lastLocation = t
                startIntentService()
            }
        })
    }

    private fun initLocation() {


        if (!isGPS) {
            Toast.makeText(context, "Please turn on GPS", Toast.LENGTH_SHORT).show()
            // return
        }
        if (!checkPermissions()) {
            // ask for permission here
            requestPermissions()
        } else {
            // getLocation()
            getLocationUpdates()
        }


    }

    private fun requestPermissions() {
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity, Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Logger.d("Displaying permission rationale to provide additional context.")
          /*  showSnackbar(
                R.string.permission_rationale, android.R.string.ok,
                View.OnClickListener {
                    // Request permission
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ), REQUEST_PERMISSIONS_REQUEST_CODE
                    )
                })*/

        } else {
            Logger.d("Requesting permission")
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //getLocation()

                    getLocationUpdates()

                } else {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context as Activity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
    }


    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */
    private fun startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        val intent = Intent(context, FetchAddressIntentService::class.java).apply {
            // Pass the result receiver as an extra to the service.
            putExtra(Constants.RECEIVER, resultReceiver)

            // Pass the location data as an extra to the service.
            putExtra(Constants.LOCATION_DATA_EXTRA, lastLocation)
        }

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        context?.startService(intent)
    }


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    private inner class AddressResultReceiver internal constructor(
        handler: Handler
    ) : ResultReceiver(handler) {

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            // Display the address string or an error message sent from the intent service.
            val addressOutput = resultData.getString(Constants.RESULT_DATA_KEY)
            Logger.d("Your city is "+ addressOutput)
            mCity = addressOutput
            loadData()
            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(context, R.string.address_found, Toast.LENGTH_SHORT).show()
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
//            addressRequested = false
        }
    }


}