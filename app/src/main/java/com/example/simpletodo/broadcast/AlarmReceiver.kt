package com.example.simpletodo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.domain.entities.ToDoItem
import com.example.simpletodo.ui.addToDo.AddTodoFragment
import com.example.simpletodo.utils.AlarmUtils
import com.example.simpletodo.utils.NotificationUtils

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("Alarm Receiver", "Receive a broadcast form system!")
        NotificationUtils.makeSoundNotification(context, "Alarm goes off", "You have a todo task!")
        val bundle = intent.getParcelableExtra<Bundle>(AlarmUtils.INTENT_KEY_EXTRA)
        val todoItem = bundle?.getSerializable(AlarmUtils.BUNDLE_KEY_EXTRA) as? ToDoItem

//        AddTodoFragment.startActivity(
//            context,
//            intent.getSerializableExtra(AlarmUtils.ALARM_KEY_EXTRA) as? ToDoItem
//        )
        //TODO:
    }
}