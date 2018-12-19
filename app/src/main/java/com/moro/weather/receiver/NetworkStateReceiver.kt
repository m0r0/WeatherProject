package com.moro.weather.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import java.util.concurrent.CopyOnWriteArrayList


/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/19/18
 * Time: 10:39 PM
 */
class NetworkStateReceiver : BroadcastReceiver() {

    companion object {
        private val listeners = CopyOnWriteArrayList<ConnectionListener>()

        fun addListener(listener: ConnectionListener) {
            listeners.add(listener)
        }

        @Synchronized
        fun removeListener(listener: ConnectionListener) {
            listeners.remove(listener)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        for (listener in listeners) {
            listener.onChange(isOnline(context))
        }
    }

    private fun isOnline(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = cm?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    interface ConnectionListener {
        fun onChange(connectionAvailable: Boolean)
    }
}