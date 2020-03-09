package com.example.simpletodo.ui.addToDo

import com.example.domain.entities.ToDoItem
import com.example.simpletodo.base.MvpView

interface Contract {
    interface View : MvpView {
        fun onUpdateDataSuccess()
        fun onUpdateDataError(err: Throwable)
        fun subscribeToAlarmManager(toDoItem: ToDoItem)
    }
}