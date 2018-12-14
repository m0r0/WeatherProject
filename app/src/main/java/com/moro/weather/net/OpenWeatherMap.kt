package com.moro.weather.net

import com.moro.weather.net.pojo.WeatherNet
import retrofit2.Call
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
    fun getWeatherForCity(@Query("q") city: String) : Call<WeatherNet>
}