package com.moro.weather.di

import android.preference.PreferenceManager
import androidx.room.Room
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.moro.weather.WeatherRepository
import com.moro.weather.db.WeatherDatabase
import com.moro.weather.main.MainViewModel
import com.moro.weather.net.OpenWeatherMap
import com.moro.weather.util.AppExecutors
import com.moro.weather.util.LiveDataCallAdapterFactory
import com.moro.weather.util.WEATHER_API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/11/18
 * Time: 6:57 PM
 */


val singletonModule = module {

    single {
        Retrofit.Builder()
            .client(OkHttpClient.Builder()
                .addInterceptor { chain -> addApiKeyToQueryParams(chain) }
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()
            )
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(
                JacksonConverterFactory.create(
                    jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                )
            )
            .baseUrl(WEATHER_API)
            .build().create(OpenWeatherMap::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), WeatherDatabase::class.java, "weather").build()
    }

    single { WeatherRepository(get(), get(), get(), get()) }

    single { AppExecutors() }

    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

}

val viewModels = module {
    viewModel { MainViewModel(get()) }
}

@GlideModule
class WeatherGlideModule : AppGlideModule()

fun addApiKeyToQueryParams(chain: Interceptor.Chain): Response {
    val oldRequest = chain.request()
    val newUrl =
        oldRequest.url().newBuilder().addQueryParameter("APPID", "a1d1dc41d71e2b1c1d329e64770bf088").build()
    val newRequest = oldRequest.newBuilder().url(newUrl).build()
    return chain.proceed(newRequest)
}