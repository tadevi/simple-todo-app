package com.example.simpletodo.base

interface MvpPresenter<T : MvpView> {
    fun attachView(mvpView: T)
    fun detachView()
    fun getView(): T?
}