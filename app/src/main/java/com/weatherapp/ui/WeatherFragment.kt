package com.weatherapp.ui

import Logger
import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.R
import com.weatherapp.model.ForecastDay
import com.weatherapp.model.ResponseForecast
import com.weatherapp.service.CurrentLocationManager
import com.weatherapp.service.FetchAddressIntentService
import com.weatherapp.u.Constants
import com.weatherapp.utils.GpsUtils
import com.weatherapp.utils.MyUtils
import com.weatherapp.utils.PermissionHelper
import com.weatherapp.viewmodel.WeatherNavigator
import com.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_error.view.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.layout_bottomsheet_temperature.*


class WeatherFragment : BaseFragment(), WeatherNavigator {


    /***
     * Represents a permission status
     */
    private var mPermissionHelper: PermissionHelper? = null

    /**
     * hold current gps status
     */
    private var isGPS: Boolean = false

    /**
     * Represents a geographical location.
     */
    private var lastLocation: Location? = null


    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    private lateinit var resultReceiver: AddressResultReceiver


    /**
     * view model object
     */
    private lateinit var mViewModel: WeatherViewModel


    /**
     * static method and const should be define here
     */
    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 34
        private const val NUM_OF_DAY_FORECATE = 4
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        mViewModel.setNavigator(this)


        mPermissionHelper = PermissionHelper(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSIONS_REQUEST_CODE
        )

        resultReceiver = AddressResultReceiver(Handler())

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        error_view.btnRetry.setOnClickListener {
            initLocation()
        }
    }


    override fun onStart() {
        super.onStart()
        initLocation()

    }

    private fun loadData(city: String) {
        if (!TextUtils.isEmpty(city)) {
            Logger.d("Weather api calling")
            if (isOnline())
                mViewModel.callWeatherForeCastApi(city, NUM_OF_DAY_FORECATE)
            else
                showSnackbar(getString(R.string.check_internet_connection), null)
        }
    }

    /**
     * bind data to bottom sheet list
     */
    private fun setAdapter(listForecastDay: List<ForecastDay>?) {
        val adapter = WeatherAdapter(listForecastDay)
        rcvWeather.layoutManager = LinearLayoutManager(context)
        rcvWeather.adapter = adapter
    }


    /**
     * start listening location changes
     */
    private fun getLocationUpdates() {
        if (!isGPS) {
            askForGPS()
        }
        context?.let {
            CurrentLocationManager.getInstance(it).observe(this,
                Observer<Location> { t ->
                    Logger.d("Location latitude observer :" + t?.latitude)
                    lastLocation = t
                    startIntentService()
                })
        }
    }

    private fun initLocation() {

        if (mPermissionHelper?.checkSelfPermission(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) == true
        ) {
            getLocationUpdates()

        } else {
            mPermissionHelper?.request(object : PermissionHelper.PermissionCallback {
                override fun onPermissionGranted() {
                    Logger.d("Permission granted")
                    getLocationUpdates()
                }

                override fun onIndividualPermissionGranted(grantedPermission: Array<String>) {
                }

                override fun onPermissionDenied() {
                    showErrorScreen()
                }

                override fun onPermissionDeniedBySystem() {
                }
            })
        }

    }


    /**
     * this will method ask to enable gps
     */
    private fun askForGPS() {
        GpsUtils(context).turnGPSOn(object : GpsUtils.onGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                Logger.d("gps enabled now")
                isGPS = isGPSEnable
                getLocationUpdates()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        mPermissionHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun showLoading() {
        loading_view.visibility = View.VISIBLE
        error_view.visibility = View.GONE
        weather_view.visibility = View.GONE

    }

    override fun showErrorScreen() {
        error_view.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        weather_view.visibility = View.GONE

    }

    override fun showWeatherScreen() {
        weather_view.visibility = View.VISIBLE
        error_view.visibility = View.GONE
        loading_view.visibility = View.GONE
    }


    override fun onForecastSuccess(responseForecast: ResponseForecast) {
        // update UI
        tvCity.text = responseForecast.location?.name

        val strTemp = MyUtils.formatTemperature(responseForecast.currentTemperature?.temp_c)

        tvTemperature.text = getString(R.string.degree, strTemp)

        setAdapter(responseForecast.forecast?.listForecastDay)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.GPS_REQUEST) {

            if (resultCode == RESULT_OK) {
                getLocationUpdates()
            } else {
                showSnackbar(getString(R.string.enable_gps), object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        askForGPS()
                    }
                })
            }
        }

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
            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                val addressOutput = resultData.getString(Constants.RESULT_DATA_KEY)
                Logger.d("Your city is " + addressOutput)
                loadData(addressOutput)
            }
        }
    }


}