package com.example.domain.usecases.base

interface BaseObserver<T> {
    fun onSuccess(data: T)
    fun onError(err: Throwable)
    fun onBeforeResult()
}