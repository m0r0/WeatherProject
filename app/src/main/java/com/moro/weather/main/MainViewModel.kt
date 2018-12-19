package com.moro.weather.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
    private val citiesList = MutableLiveData<String>()

    private val weather: LiveData<Resource<List<Weather>>> = Transformations.switchMap(citiesList) {
        repository.loadWeather(it)
    }

    fun weather(): LiveData<Resource<List<Weather>>> = weather

    fun setCities(vararg cityIds: String) {
        val cityList = cityIds.joinToString(",")
        if (citiesList.value != cityList) {
            citiesList.value = cityList
        }
    }

    fun forceWeatherUpdate() {
        citiesList.value?.let {
            citiesList.value = it
        }
    }
}