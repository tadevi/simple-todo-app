package com.example.simpletodo.ui.addToDo

import com.example.domain.entities.ToDoItem
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.domain.usecases.UpdateToDoItemUseCase
import com.example.simpletodo.base.BaseMvpPresenter
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AddToDoPresenter(
    private val updateToDoItemUseCase: UpdateToDoItemUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) :
    BaseMvpPresenter<Contract.View>() {
    private var view: Contract.View? = null

    fun updateOrInsertData(toDoItem: ToDoItem, update: Boolean) {
        val subscriber = object : BaseObserver<Int> {
            override fun onComplete() {

            }
            override fun onSuccess(data: Int) {
                getView()?.onUpdateDataSuccess()
            }

            override fun onError(error: Throwable) {
                getView()?.onUpdateDataError(error)
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