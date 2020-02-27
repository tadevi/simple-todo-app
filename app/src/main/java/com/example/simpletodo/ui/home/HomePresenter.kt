package com.example.simpletodo.ui.home

import com.example.domain.entities.ToDoItem
import com.example.domain.usecases.DeleteToDoItemUseCase
import com.example.domain.usecases.GetToDoListUseCase
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.simpletodo.base.BaseMvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter constructor(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) : BaseMvpPresenter<Contract.View>() {
    private var lastItemDeleted: ToDoItem? = null

    fun loadData() {
        executeUseCase(
            getToDoListUseCase,
            GetToDoListUseCase.Request(),
            object : BaseObserver<List<ToDoItem>> {
                override fun onComplete() {

                }

                override fun onSuccess(data: List<ToDoItem>) {
                    getView()?.onRetrieveListTodoSuccess(data)
                }

                override fun onError(error: Throwable) {
                    getView()?.onRetrieveListTodoError(error)
                }
            })
    }

    fun deleteItem(toDoItem: ToDoItem) {
        executeUseCase(
            deleteToDoItemUseCase,
            DeleteToDoItemUseCase.Request(toDoItem),
            object : BaseObserver<Int> {
                override fun onComplete() {

                }

                override fun onSuccess(data: Int) {
                    lastItemDeleted = toDoItem
                    getView()?.onDeleteItemSuccess()
                }

                override fun onError(error: Throwable) {
                    getView()?.onDeleteItemError(error)
                }
            })
    }

    fun undoItem() {
        lastItemDeleted?.let {
            executeUseCase(
                insertToDoItemUseCase,
                InsertToDoItemUseCase.Request(it),
                object : BaseObserver<Int> {
                    override fun onComplete() {

                    }

                    override fun onSuccess(data: Int) {

                    }

                    override fun onError(error: Throwable) {

                    }
                })
        }
    }
}