package com.example.services

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   private val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
   }
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.simpleService.setOnClickListener {
            stopService(MyForegroundService.newIntent(this))
            startService(MyService.newIntent(this))
        }
        binding.foregroundService.setOnClickListener {
            startForegroundService(MyForegroundService.newIntent(this))
        }
        binding.intentService.setOnClickListener {
            startForegroundService(MyIntentService.newIntent(this))
        }
        binding.jobScheduler.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)

            val jobInfo = JobInfo.Builder(MyJobService.JOB_ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

            val intent = MyJobService.newIntent(page++)
            jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
        }

        binding.jobIntentService.setOnClickListener {
            MyJobIntentService.enqueue(this, page++)
        }
    }
}