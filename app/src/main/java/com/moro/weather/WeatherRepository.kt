package com.moro.weather

import com.moro.weather.db.WeatherDatabase
import retrofit2.Retrofit

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/14/18
 * Time: 5:08 PM
 */
class WeatherRepository(val retrofit: Retrofit, val db: WeatherDatabase) {
    fun loadWeather() {
        // Query cache or fetch from network
    }
}