package com.example.domain.usecases.base

import io.reactivex.Observable


interface ObservableUseCase<T, R> :
    UseCase<T, Observable<R>>