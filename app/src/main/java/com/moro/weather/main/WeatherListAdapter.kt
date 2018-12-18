package com.moro.weather.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moro.weather.BR
import com.moro.weather.databinding.ItemWeatherBinding
import com.moro.weather.db.pojo.Weather
import com.moro.weather.di.GlideApp
import com.moro.weather.util.WEATHER_IMAGE_URL_PREFIX
import java.util.*

/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/15/18
 * Time: 8:24 PM
 */
class WeatherListAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private var weatherList: List<Weather> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder =
        WeatherViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = weatherList.size

    private fun getItem(position: Int) = weatherList[position]

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.binding.setVariable(BR.weather, getItem(position))
        holder.binding.executePendingBindings()
    }

    fun submitData(data: List<Weather>) {
        weatherList = data
        notifyDataSetChanged()
    }
}

class WeatherViewHolder(val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root)

@BindingAdapter("image_id")
fun loadImage(imageView: ImageView, imageId: String) {
    GlideApp.with(imageView.context)
        .load("$WEATHER_IMAGE_URL_PREFIX$imageId.png")
        .into(imageView)
}