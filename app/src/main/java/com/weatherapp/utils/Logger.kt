import android.util.Log

class Logger {
    companion object {
        private const val TAG = "logger"
        @JvmStatic
        fun d(format: String) {
            Log.d(TAG, format)
        }
        fun e(format: String) {
            Log.e(TAG, format)
        }

    }
}