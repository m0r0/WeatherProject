package com.moro.weather

import androidx.lifecycle.LiveData
import com.moro.weather.db.WeatherDatabase
import com.moro.weather.db.pojo.Weather
import com.moro.weather.net.ApiResponse
import com.moro.weather.net.NetworkBoundResource
import com.moro.weather.net.OpenWeatherMap
import com.moro.weather.net.pojo.WeatherResponse
import com.moro.weather.util.AppExecutors
import com.moro.weather.util.RateLimiter
import com.moro.weather.util.Resource
import java.util.concurrent.TimeUnit

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/14/18
 * Time: 5:08 PM
 */
class WeatherRepository(
    private val retrofit: OpenWeatherMap,
    private val db: WeatherDatabase,
    private val appExecutors: AppExecutors
) {
    private val weatherRateLimiter = RateLimiter<String>(3, TimeUnit.SECONDS)

    fun loadWeather(vararg cityIds: String): LiveData<Resource<List<Weather>>> {
        val cityIdsParam = cityIds.joinToString(",")
        return object : NetworkBoundResource<List<Weather>, WeatherResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<WeatherResponse>> =
                retrofit.getWeatherForCities(cityIdsParam)

            override fun saveCallResult(item: WeatherResponse) {
                db.weatherDao().insertWeather(item.list.map { Weather(it) })
            }

            override fun loadFromDb(): LiveData<List<Weather>> = db.weatherDao().getAll()

            override fun shouldFetch(data: List<Weather>?): Boolean =
                data == null || data.isEmpty() || weatherRateLimiter.shouldFetch(cityIdsParam)
        }.asLiveData()
    }
}