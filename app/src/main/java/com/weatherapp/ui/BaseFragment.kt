package com.weatherapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.weatherapp.MainActivity

open class BaseFragment : Fragment() {

    public lateinit var mActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getActivity() is MainActivity) {
            mActivity = activity as MainActivity
        }

    }


    /**
     * Shows a [Snackbar].
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    fun showSnackbar(
        message: String, listener: View.OnClickListener?
    ) {
        Snackbar.make(
            mActivity.findViewById(android.R.id.content), message,
            if (listener != null) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_SHORT)
            .setAction(getString(android.R.string.ok), listener)
            .show()
    }


    fun isOnline(): Boolean {
        val networkTypes = intArrayOf(ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI)
        try {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            for (networkType in networkTypes) {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.type == networkType)
                    return true
            }
        } catch (e: Exception) {
            return false
        }

        return false
    }

}