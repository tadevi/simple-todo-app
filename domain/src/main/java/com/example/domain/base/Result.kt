package com.example.domain.base

import io.reactivex.Observable

sealed class Result<out T> {
    data class OnSuccess<out T>(val data: T) : Result<T>()
    data class OnError<out T>(val error: Throwable) : Result<T>()
}


fun <T> Result<T>.toObservable(): Observable<T>{
    return when(this){
        is Result.OnSuccess->Observable.just(this.data)
        is Result.OnError-> Observable.error(this.error)
    }
}