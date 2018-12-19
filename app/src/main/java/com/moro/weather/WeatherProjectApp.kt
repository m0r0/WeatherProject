package com.moro.weather

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.moro.weather.di.singletonModule
import com.moro.weather.di.viewModels
import org.koin.android.ext.android.startKoin

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/11/18
 * Time: 7:20 PM
 */
class WeatherProjectApp : Application() {
    override fun onCreate() {
        super.onCreate()
        jacksonObjectMapper()
        startKoin(this, listOf(singletonModule, viewModels))
        registerReceiver(NetworkStateReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
}