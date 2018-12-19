package com.moro.weather.main

import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.moro.weather.NetworkStateReceiver
import com.moro.weather.R
import com.moro.weather.databinding.ActivityMainBinding
import com.moro.weather.util.KEY_LAST_SYNC_TIME
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private val kyivId = "703448"
    private val londonId = "2643744"
    private val torontoId = "6167865"

    private val mainViewModel by viewModel<MainViewModel>()
    private val preferences: SharedPreferences by inject()


    private val connectionListener = object : NetworkStateReceiver.ConnectionListener {
        override fun onChange(connectionAvailable: Boolean) {
            onNetworkStateChanged(connectionAvailable)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        swipe_container.setOnRefreshListener { onRefreshTriggered() }
        val adapter = WeatherListAdapter()
        list.layoutManager = (LinearLayoutManager(this))
        list.adapter = adapter
        mainViewModel.weather().observe(this, Observer {
            when {
                it.isSuccess() -> {
                    swipe_container.isRefreshing = false
                    adapter.submitData(it.data ?: Collections.emptyList())
                }
                it.isFailure() -> {
                    swipe_container.isRefreshing = false
                    Log.e(javaClass.canonicalName, it.error)
                }
                else -> {
                    swipe_container.isRefreshing = true
                }
            }
        })
        mainViewModel.setCities(kyivId, londonId, torontoId)
        NetworkStateReceiver.addListener(connectionListener)
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

    override fun onDestroy() {
        super.onDestroy()
        NetworkStateReceiver.removeListener(connectionListener)
    }

    private fun onRefreshTriggered(): Boolean {
        mainViewModel.forceWeatherUpdate()
        return true
    }

    private fun onNetworkStateChanged(isConnected: Boolean) {
        if (isConnected) {
            offline_view.visibility = View.GONE
            onRefreshTriggered()
        } else {
            offline_view.visibility = View.VISIBLE
            offline_view.text = getString(
                R.string.offline_mode_text, DateFormat.getTimeFormat(this).format(
                    Date(preferences.getLong(KEY_LAST_SYNC_TIME, System.currentTimeMillis()))
                )
            )
        }
    }
}
