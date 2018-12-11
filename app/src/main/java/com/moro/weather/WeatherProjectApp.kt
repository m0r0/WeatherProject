package com.moro.weather

import android.app.Application
import com.moro.weather.di.netModule
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
        startKoin(this, listOf(netModule))
    }
}