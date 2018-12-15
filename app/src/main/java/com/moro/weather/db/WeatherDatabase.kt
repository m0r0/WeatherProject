package com.moro.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moro.weather.db.pojo.Weather

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/13/18
 * Time: 11:50 PM
 */
@Database(entities = [Weather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao() : WeatherDao
}