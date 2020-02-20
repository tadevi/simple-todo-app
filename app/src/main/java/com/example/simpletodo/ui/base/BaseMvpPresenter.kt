package com.example.simpletodo.ui.base

import com.example.domain.usecases.base.BaseObserver
import com.example.domain.usecases.base.CompletableUseCase
import com.example.domain.usecases.base.ObservableUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseMvpPresenter<T : MvpView> : MvpPresenter<T> {
    private var mvpView: T? = null
    private val compositeDisposable = CompositeDisposable()

    fun <T, R> executeUseCase(
        useCase: ObservableUseCase<T, R>,
        param: T,
        observer: BaseObserver<R>,
        scheduler: Scheduler = Schedulers.io()
    ) {
        val disposable = useCase.executeUseCase(param)
            .subscribeOn(scheduler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    observer.onSuccess(it)
                },
                {
                    observer.onError(it)
                },
                {
                    observer.onBeforeResult()
                }
            )

        compositeDisposable.add(disposable)
    }

    fun <T> executeUseCase(
        useCase: CompletableUseCase<T>,
        param: T,
        observer: BaseObserver<T>,
        scheduler: Scheduler = Schedulers.io()
    ) {
        val disposable = useCase.executeUseCase(param)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(scheduler)
            .subscribe(
                {
                    observer.onBeforeResult()
                },
                {
                    observer.onError(it)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onAttach(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        this.mvpView = null
        compositeDisposable.clear()
    }

    override fun getMvpView(): T? {
        return mvpView
    }
}