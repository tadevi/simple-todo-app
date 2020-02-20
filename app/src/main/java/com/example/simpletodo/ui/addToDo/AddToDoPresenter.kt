package com.example.simpletodo.ui.addToDo

import com.example.domain.entities.ToDoItem
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.domain.usecases.UpdateToDoItemUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddToDoPresenter @Inject constructor(
    private val updateToDoItemUseCase: UpdateToDoItemUseCase,
    private val insertToDoItemUseCase: InsertToDoItemUseCase
) :
    Contract.Presenter {
    private var view: Contract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun updateOrInsertData(toDoItem: ToDoItem, update: Boolean) {
        val useCase = if (update) updateToDoItemUseCase else insertToDoItemUseCase

        val disposable = useCase
            .executeUseCase(toDoItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    getView()?.onUpdateDataSuccess()
                },
                {
                    getView()?.onUpdateDataError(it)
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun onAttach(view: Contract.View) {
        this.view = view
    }

    override fun onDetach() {
        this.view = null
        compositeDisposable.dispose()
    }

    override fun getView(): Contract.View? {
        return view
    }
}