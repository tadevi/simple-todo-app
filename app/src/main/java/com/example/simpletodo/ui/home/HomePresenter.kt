package com.example.simpletodo.ui.home

import com.example.domain.usecases.GetToDoListUseCase
import com.example.domain.usecases.base.NoParam
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val getToDoListUseCase: GetToDoListUseCase
) : Contract.Presenter {
    private var mvpView: Contract.View? = null
    private val compositeDisposable = CompositeDisposable()
    override fun loadData() {
        compositeDisposable.add(
            getToDoListUseCase
                .executeUseCase(NoParam())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        getView()?.onRetrieveListTodoSuccess(it)
                    },
                    {
                        getView()?.onRetrieveListTodoError(it)
                    }
                )
        )
    }

    override fun onAttach(view: Contract.View) {
        mvpView = view
    }

    override fun onDetach() {
        mvpView = null
    }

    override fun getView(): Contract.View? {
        return mvpView
    }
}