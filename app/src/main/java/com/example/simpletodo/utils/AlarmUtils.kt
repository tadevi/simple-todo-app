package com.example.simpletodo.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.AlarmManagerCompat
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.broadcast.AlarmReceiver

object AlarmUtils {
    const val ALARM_ACTION = "com.example.simpletodo.alarm_action"
    const val INTENT_KEY_EXTRA = "alarm key extra"
    const val BUNDLE_KEY_EXTRA = "bundle key extra"

    fun updateAlarm(context: Context, toDoItem: ToDoItem) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        val bundle = Bundle()
        bundle.putSerializable(BUNDLE_KEY_EXTRA, toDoItem)
        intent.putExtra(INTENT_KEY_EXTRA, bundle)

        val pendingIntent = PendingIntent.getBroadcast(context, toDoItem.id, intent, 0)

        toDoItem.datetime?.let {
            AlarmManagerCompat.setAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                it.timeInMillis,
                pendingIntent
            )
        } ?: run {
            alarmManager.cancel(pendingIntent)
        }
    }
}