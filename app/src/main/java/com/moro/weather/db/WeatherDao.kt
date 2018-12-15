package com.moro.weather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moro.weather.db.pojo.Weather

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/12/18
 * Time: 7:29 PM
 */
@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun getAll(): LiveData<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherList: List<Weather>)
}