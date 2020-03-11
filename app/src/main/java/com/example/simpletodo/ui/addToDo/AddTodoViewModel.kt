package com.example.simpletodo.ui.addToDo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.entities.ToDoItem
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.domain.usecases.UpdateToDoItemUseCase
import com.example.simpletodo.base.BaseViewModel
import javax.inject.Inject

sealed class Result {
    data class Error(val e: Throwable) : Result()
    data class Success(val item: ToDoItem) : Result()
}



class AddTodoViewModel @Inject constructor(
    private val updateToDoItemUseCase: UpdateToDoItemUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) : BaseViewModel() {
    private val result = MutableLiveData<Result?>()

    fun getResult(): LiveData<Result?> {
        return result
    }

    fun updateOrInsertData(toDoItem: ToDoItem, update: Boolean) {
        val subscriber = object : BaseObserver<Int> {
            override fun onComplete() {

            }

            override fun onSuccess(data: Int) {
                result.postValue(Result.Success(toDoItem))
            }

            override fun onError(error: Throwable) {
                result.postValue(Result.Error(error))
            }
        }
        if (update) {
            executeUseCase(
                updateToDoItemUseCase,
                UpdateToDoItemUseCase.Request(toDoItem),
                subscriber
            )

        } else {
            executeUseCase(
                insertToDoItemUseCase,
                InsertToDoItemUseCase.Request(toDoItem),
                subscriber
            )
        }
    }
}