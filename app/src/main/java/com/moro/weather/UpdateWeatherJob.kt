package com.moro.weather

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.moro.weather.db.WeatherDatabase
import com.moro.weather.db.pojo.Weather
import com.moro.weather.net.OpenWeatherMap
import com.moro.weather.net.pojo.WeatherResponse
import com.moro.weather.util.cityIds
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/20/18
 * Time: 12:07 AM
 */
class UpdateWeatherJob : JobService() {

    private val retrofit: OpenWeatherMap by inject()
    private val db: WeatherDatabase by inject()

    private lateinit var getWeatherCall: Call<WeatherResponse>

    override fun onStartJob(params: JobParameters?): Boolean {
        getWeatherCall = retrofit.getWeatherForCitiesJobService(cityIds)
        getWeatherCall.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    db.weatherDao().insertWeather(response.body()?.list?.map { Weather(it) } ?: Collections.emptyList())
                } else {
                    Log.e(this.javaClass.simpleName, response.errorBody()?.string() ?: response.message())
                }
                jobFinished(params, !response.isSuccessful)
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.message)
                jobFinished(params, true)
            }
        })
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        getWeatherCall.cancel()
        return true
    }
}