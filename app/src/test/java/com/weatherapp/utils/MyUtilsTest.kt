package com.weatherapp.utils

import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.text.DecimalFormat

class MyUtilsTest {

    @Before
    fun setUp() {
    }


    /**
     * date format is yyyy-mm-dd
     * on 2019-08-21 it should be Wednesday
     */
    //positive case
    @Test
    fun test1GetDayFromDate(){
       assertEquals("Wednesday",MyUtils.getDayFromDate("2019-08-21"))

    }
    // invalid date format
    @Test
    fun test2GetDayFromDate(){
        assertEquals("--",MyUtils.getDayFromDate("2019/08/21"))

    }

    //null or empty
    @Test
    fun test3GetDayFromDate(){
        assertEquals("--",MyUtils.getDayFromDate(null))

    }

    /**
     * date format is ###.#
     * input -- 30.0
     * output --- 30
     */
    @Test
    fun test1formatTemperature(){
        assertEquals("30",MyUtils.formatTemperature(30.0))
    }


    @Test
    fun test2formatTemperature(){
        assertEquals("0",MyUtils.formatTemperature(0.0))
    }


    @Test
    fun test3formatTemperature(){
        assertEquals("100.2",MyUtils.formatTemperature(100.215))
    }


    @After
    fun tearDown() {
    }
}