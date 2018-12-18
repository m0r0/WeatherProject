package com.moro.weather.net

import androidx.lifecycle.LiveData
import com.moro.weather.net.pojo.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/11/18
 * Time: 7:12 PM
 */
interface OpenWeatherMap {
    @GET("group")
    fun getWeatherForCities(@Query("id", encoded = true) cityIds: String): LiveData<ApiResponse<WeatherResponse>>
}