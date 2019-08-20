package com.test.gojek.network

import android.util.Log

class Logger {
    companion object {
        private const val TAG = "logger"
        @JvmStatic
        fun d(format: String) {
            Log.d(TAG, format)
        }
    }
}