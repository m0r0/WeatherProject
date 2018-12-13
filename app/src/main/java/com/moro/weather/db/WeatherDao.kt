package com.moro.weather.db

import androidx.room.Query
import com.moro.weather.db.pojo.Weather

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/12/18
 * Time: 7:29 PM
 */
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getAll() : List<Weather>
}