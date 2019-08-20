package com.weatherapp.utils

import com.weatherapp.u.Constants
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object MyUtils {
    //2019-08-21
    val dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)

    val DayOfWeek = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    fun getDayFromDate(strDate: String?): String {
        if(strDate == null)
            return Constants.EMPTY_DAY
        try {
            val date = dateFormat.parse(strDate)
            val cal = Calendar.getInstance()
            cal.time = date
            return DayOfWeek[cal.get(Calendar.DAY_OF_WEEK)]
        } catch (ex: ParseException){
            ex.printStackTrace()
            return Constants.EMPTY_DAY
        }
    }

    fun formatTemperature(temp: Double?): String {
        val df = DecimalFormat("###.#")
        return df.format(temp)
    }

}