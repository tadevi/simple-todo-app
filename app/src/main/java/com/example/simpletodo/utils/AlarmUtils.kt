package com.example.simpletodo.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.broadcast.AlarmReceiver
import com.example.simpletodo.ui.addToDo.AddToDoActivity
import java.util.*

object AlarmUtils {
    const val ALARM_ACTION = "com.example.simpletodo.alarm_action"
    const val ALARM_KEY_EXTRA = "alarm key extra"

    fun updateAlarm(context: Context, toDoItem: ToDoItem) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = ALARM_ACTION
        intent.putExtra(ALARM_KEY_EXTRA, toDoItem)

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