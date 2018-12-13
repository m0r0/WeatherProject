package com.moro.weather.db.pojo

import androidx.room.PrimaryKey

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/12/18
 * Time: 7:30 PM
 */
data class Weather(
    @PrimaryKey var uid: Int,
    var city: String,
    var temperature: String,
    var icon: String,
    var description: String
)