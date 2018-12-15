package com.moro.weather.net.pojo

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/14/18
 * Time: 12:32 AM
 */
data class WeatherResponse(val cnt: Int, val list: List<WeatherInfo>)

data class WeatherInfo(val id: Int, val weather: List<WeatherConditions>, val main: Temperature, val name: String)

data class WeatherConditions(val id: Int, val main: String, val description: String, val icon: String)

data class Temperature(val temp: Float, val pressure: Int, val humidity: Int)