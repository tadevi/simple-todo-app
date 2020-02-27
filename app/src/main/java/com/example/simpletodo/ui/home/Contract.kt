package com.example.simpletodo.ui.home

import com.example.domain.entities.ToDoItem
import com.example.simpletodo.base.MvpView

interface Contract {
    interface View: MvpView {
        fun onRetrieveListTodoSuccess(data: List<ToDoItem>)
        fun onRetrieveListTodoError(err: Throwable)
        fun onDeleteItemSuccess()
        fun onDeleteItemError(err: Throwable)
    }
}