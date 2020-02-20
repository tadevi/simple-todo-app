package com.example.simpletodo.ui.addToDo

import com.example.domain.entities.ToDoItem

interface Contract {
    interface View {
        fun onUpdateDataSuccess()
        fun onUpdateDataError(err: Throwable)
    }

    interface Presenter {
        fun updateOrInsertData(toDoItem: ToDoItem, update: Boolean = false)
        fun onAttach(view: View)
        fun onDetach()
        fun getView(): View?
    }
}