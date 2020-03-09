package com.example.simpletodo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.simpletodo.work_manager.UpdateAlarmManagerWorker

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val workRequest = OneTimeWorkRequest.Builder(UpdateAlarmManagerWorker::class.java)
            .build()
        context?.let {
            WorkManager.getInstance(it).enqueue(workRequest)
        }
    }
}