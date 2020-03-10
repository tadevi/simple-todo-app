package com.example.simpletodo.base

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.BaseUseCase
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    interface BaseObserver<R> {
        fun onComplete()
        fun onSuccess(data: R)
        fun onError(error: Throwable)
    }

    open fun getBackgroundScheduler(): Scheduler {
        return Schedulers.io()
    }

    open fun getForegroundScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    protected fun <T> Observable<T>.subscribe(observer: BaseObserver<T>) {
        this.subscribe(object : Observer<T> {
            override fun onComplete() {
                observer.onComplete()
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.addAll(d)
            }

            override fun onNext(t: T) {
                observer.onSuccess(t)
            }

            override fun onError(e: Throwable) {
                observer.onError(e)
            }
        })
    }

    protected fun <U : BaseUseCase.BaseRequest, R> executeUseCase(
        useCase: BaseUseCase<U, R>,
        request: U,
        observer: BaseObserver<R>
    ) {
        val observable = useCase
            .execute(request)
            .observeOn(getForegroundScheduler())
            .subscribeOn(getBackgroundScheduler())

        observable.subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}