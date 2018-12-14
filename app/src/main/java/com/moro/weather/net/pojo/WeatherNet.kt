package com.moro.weather.net.pojo

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/14/18
 * Time: 12:32 AM
 */

data class WeatherNet(val weatherList: List<WeatherConditions>, val main: Temperature, val name: String)

data class WeatherConditions(val id: Int, val main: String, val description: String, val icon: String)

data class Temperature(val temp: Float, val pressure: Int, val humidity: Int)