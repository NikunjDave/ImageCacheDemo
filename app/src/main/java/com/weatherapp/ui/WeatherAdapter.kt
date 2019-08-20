package com.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.model.ForecastDay
import com.weatherapp.utils.MyUtils
import kotlinx.android.synthetic.main.layout_item_temperature.view.*

class WeatherAdapter(private val forecastList: List<ForecastDay>?) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_temperature, parent, false)
        return MyViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return forecastList?.count() ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = forecastList?.get(position)
        if (item != null) {
            val dayName = MyUtils.getDayFromDate(item.date)
            holder.itemView.tvDay.text = dayName
            val strTemp = MyUtils.formatTemperature(item.day?.avgtemp_c)
            holder.itemView.tvDayTemperature.text = strTemp.plus("C")
        }
    }

    class MyViewHolder(private val rowView: View) : RecyclerView.ViewHolder(rowView)


}