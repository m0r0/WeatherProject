package com.moro.weather.db.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moro.weather.net.pojo.WeatherInfo
import com.moro.weather.util.fromKelvinToCelsius
import kotlin.math.roundToInt

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/12/18
 * Time: 7:30 PM
 */
@Entity
data class Weather(
    @PrimaryKey var cityId: Int,
    var city: String,
    var temperature: String,
    var icon: String,
    var description: String
) {
    constructor(weatherInfo: WeatherInfo) : this(
        weatherInfo.id,
        weatherInfo.name,
        "${weatherInfo.main.temp.fromKelvinToCelsius().roundToInt()}˚",
        weatherInfo.weather[0].icon,
        weatherInfo.weather[0].description.capitalize()
    )
}