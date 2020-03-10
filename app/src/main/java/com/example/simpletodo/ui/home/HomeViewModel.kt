package com.example.simpletodo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entities.ToDoItem
import com.example.domain.usecases.DeleteToDoItemUseCase
import com.example.domain.usecases.GetToDoListUseCase
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.simpletodo.base.BaseViewModel
import com.example.simpletodo.dagger.scope.ViewModelScope
import com.example.simpletodo.ui.addToDo.Result
import javax.inject.Inject


@ViewModelScope
class HomeViewModel @Inject constructor(
    private val getToDoListUseCase: GetToDoListUseCase,
    private val deleteToDoItemUseCase: DeleteToDoItemUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) : BaseViewModel() {
    private val lastItemDeleted = MutableLiveData<ToDoItem?>()
    private val listTodoItem = MutableLiveData<List<ToDoItem>>()
    private val resultStatus = MutableLiveData<Result?>()

    fun getResultStatus(): LiveData<Result?> {
        return resultStatus
    }

    fun getListTodoItem(): LiveData<List<ToDoItem>> {
        return listTodoItem
    }

    fun loadData() {
        executeUseCase(
            getToDoListUseCase,
            GetToDoListUseCase.Request(),
            object : BaseObserver<List<ToDoItem>> {
                override fun onComplete() {

                }

                override fun onSuccess(data: List<ToDoItem>) {
                    listTodoItem.postValue(data)
                }

                override fun onError(error: Throwable) {
                    resultStatus.postValue(Result.Error(error))
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
                    lastItemDeleted.postValue(toDoItem)
                }

                override fun onError(error: Throwable) {
                    resultStatus.postValue(Result.Error(error))
                }
            })
    }

    fun undoItem() {
        lastItemDeleted.value?.let {
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