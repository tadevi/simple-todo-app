package com.example.simpletodo.work_manager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.GetToDoListUseCase
import com.example.simpletodo.utils.AlarmUtils
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class UpdateAlarmManagerWorker(private val context: Context, private val params: WorkerParameters) :
    Worker(context, params) {

    private val repository: ToDoRepository by inject(ToDoRepository::class.java)

    override fun doWork(): Result {
        Log.d("UpdateAlarmWorker", "Work Manager started")
        val todos =
            GetToDoListUseCase(repository).execute(GetToDoListUseCase.Request()).blockingFirst()

        for (todo in todos) {
            todo.datetime?.let {
                if (!todo.isFinish && it.timeInMillis < Calendar.getInstance().timeInMillis) {
                    AlarmUtils.updateAlarm(context, todo)
                }
            }
        }
        return Result.success()
    }

    override fun onStopped() {
        Log.d("UpdateAlarmWorker", "Work Manager stopped")
        super.onStopped()
    }
}