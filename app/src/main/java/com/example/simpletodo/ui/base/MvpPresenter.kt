package com.example.simpletodo.ui.base

interface MvpPresenter<T : MvpView> {
    fun onAttach(mvpView: T)
    fun onDetach()
    fun getMvpView(): T?
}