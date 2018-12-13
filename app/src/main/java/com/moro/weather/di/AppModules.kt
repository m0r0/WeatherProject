package com.moro.weather.di

import androidx.room.Room
import com.moro.weather.db.WeatherDatabase
import com.moro.weather.net.OpenWeatherMap
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/11/18
 * Time: 6:57 PM
 */


val netModule = module {

    single {
        Retrofit.Builder()
            .client(OkHttpClient.Builder().addInterceptor { chain -> addApiKeyToQueryParams(chain) }.build())
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("api.openweathermap.org/data/2.5/")
            .build().create(OpenWeatherMap::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), WeatherDatabase::class.java, "weather").build()
    }

}

fun addApiKeyToQueryParams(chain: Interceptor.Chain): Response {
    val oldRequest = chain.request()
    val newUrl =
        oldRequest.url().newBuilder().addQueryParameter("APPID", "a1d1dc41d71e2b1c1d329e64770bf088").build()
    val newRequest = oldRequest.newBuilder().url(newUrl).build()
    return chain.proceed(newRequest)
}