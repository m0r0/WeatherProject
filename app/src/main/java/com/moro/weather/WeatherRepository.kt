package com.moro.weather

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import com.moro.weather.db.WeatherDatabase
import com.moro.weather.db.pojo.Weather
import com.moro.weather.net.ApiResponse
import com.moro.weather.net.NetworkBoundResource
import com.moro.weather.net.OpenWeatherMap
import com.moro.weather.net.pojo.WeatherResponse
import com.moro.weather.util.AppExecutors
import com.moro.weather.util.KEY_LAST_SYNC_TIME
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
    private val appExecutors: AppExecutors,
    private val preferences: SharedPreferences
) {
    private val weatherRateLimiter = RateLimiter<String>(1, TimeUnit.SECONDS)

    fun loadWeather(cityIds: String): LiveData<Resource<List<Weather>>> =
        object : NetworkBoundResource<List<Weather>, WeatherResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<WeatherResponse>> =
                retrofit.getWeatherForCities(cityIds)

            override fun saveCallResult(item: WeatherResponse) {
                db.weatherDao().insertWeather(item.list.map { Weather(it) })
                preferences.edit {
                    putLong(KEY_LAST_SYNC_TIME, System.currentTimeMillis())
                }
            }

            override fun loadFromDb(): LiveData<List<Weather>> = db.weatherDao().getAll()

            override fun shouldFetch(data: List<Weather>?): Boolean =
                data == null || data.isEmpty() || weatherRateLimiter.shouldFetch(cityIds)

            override fun onFetchFailed() {
                weatherRateLimiter.reset(cityIds)
            }
        }.asLiveData()
}