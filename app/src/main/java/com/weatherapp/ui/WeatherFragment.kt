package com.weatherapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hkuaapp.network.ApiService
import com.test.gojek.Model.ResponseLocation
import com.test.gojek.Model.Weather
import com.test.gojek.network.Logger
import com.test.gojek.ui.WeatherAdapter
import com.test.gojek.ui.WeatherViewModel
import com.test.gojek.viewmodel.WeatherNavigator
import com.weatherapp.R
import kotlinx.android.synthetic.main.layout_bottomsheet_temperature.*

class WeatherFragment : Fragment(), WeatherNavigator {
        override fun onLocationFetched(location: ResponseLocation) {
            Logger.d("Weather api success")
            setAdapter()

        }

        override fun onLocationError() {
            Logger.d("Weather api failed")
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
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_weather, container, false)
            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            loadData()

        }

        private fun loadData() {
            if (!TextUtils.isEmpty(mCity)) {
                Logger.d("Weather api calling")
                mViewModel.callWeatherApi(mCity)
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


    }