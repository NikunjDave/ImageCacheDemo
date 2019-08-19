package com.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.gojek.Model.Weather
import com.test.gojek.ui.WeatherAdapter
import com.weatherapp.R
import kotlinx.android.synthetic.main.layout_bottomsheet_temperature.*

class WeatherFragment : Fragment(){

    companion object {
        fun newInstance(): Fragment {
            return WeatherFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()

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