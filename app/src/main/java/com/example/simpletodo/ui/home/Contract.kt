package com.example.simpletodo.ui.home

import com.example.domain.entities.ToDoItem

interface Contract {
    interface View {
        fun onRetrieveListTodoSuccess(data: List<ToDoItem>)
        fun onRetrieveListTodoError(err: Throwable)
    }

    interface Presenter {
        fun loadData()
        fun onAttach(view: View)
        fun onDetach()
        fun getView(): View?
    }
}