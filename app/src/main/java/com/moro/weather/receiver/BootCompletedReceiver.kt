package com.moro.weather.receiver

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.moro.weather.UpdateWeatherJob


/**
 * Created with Android Studio.
 * User: Vitalii Morozov
 * Date: 12/20/18
 * Time: 12:00 AM
 */
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context?, intent: Intent?) {
        ctx?.let { context ->
            if (intent?.action?.equals(Intent.ACTION_BOOT_COMPLETED) == true) {
                val serviceComponent = ComponentName(context, UpdateWeatherJob::class.java)
                val builder = JobInfo.Builder(7070, serviceComponent)
                builder.setPeriodic(60 * 60 * 1000) // 1 hour
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
                jobScheduler.schedule(builder.build())
            }
        }
    }
}