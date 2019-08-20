package com.weatherapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.model.Weather
import kotlinx.android.synthetic.main.layout_item_temperature.view.*

class WeatherAdapter(private val list: ArrayList<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_temperature, parent, false)
        return MyViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.tvDay.text = item.day
        holder.itemView.tvDayTemperature.text = item.temperature
    }

    class MyViewHolder(private val rowView: View) : RecyclerView.ViewHolder(rowView)
}