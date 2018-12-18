package com.moro.weather.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.moro.weather.WeatherRepository
import com.moro.weather.db.pojo.Weather
import com.moro.weather.util.Resource

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/15/18
 * Time: 7:45 PM
 */
class MainViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val kyivId = "703448"
    private val londonId = "2643744"
    private val torontoId = "6167865"
    fun getWeather(): LiveData<Resource<List<Weather>>> {
        return repository.loadWeather(kyivId, londonId, torontoId)
    }
}