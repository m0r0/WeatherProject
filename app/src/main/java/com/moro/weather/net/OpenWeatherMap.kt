package com.moro.weather.net

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/11/18
 * Time: 7:12 PM
 */
interface OpenWeatherMap {
    @GET("weather")
    fun getWeatherForCity(@Query("q") city: String)
}