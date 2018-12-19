package com.moro.weather.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.moro.weather.R
import com.moro.weather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private val kyivId = "703448"
    private val londonId = "2643744"
    private val torontoId = "6167865"

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewRoot = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val swipeRefreshLayout = viewRoot.swipeContainer
        swipeRefreshLayout.setOnRefreshListener { onRefreshTriggered() }
        val list = viewRoot.list
        val adapter = WeatherListAdapter()
        list.layoutManager = (LinearLayoutManager(this))
        list.adapter = adapter
        mainViewModel.weather().observe(this, Observer {
            when {
                it.isSuccess() -> {
                    swipeRefreshLayout.isRefreshing = false
                    adapter.submitData(it.data ?: Collections.emptyList())
                }
                it.isFailure() -> {
                    swipeRefreshLayout.isRefreshing = false
                    Log.e(javaClass.canonicalName, it.error)
                    Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                }
                else -> {
                    swipeRefreshLayout.isRefreshing = true
                }
            }
        })
        mainViewModel.setCities(kyivId, londonId, torontoId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_refresh -> onRefreshTriggered()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onRefreshTriggered(): Boolean {
        mainViewModel.forceWeatherUpdate()
        return true
    }
}
