package com.example.domain.usecases.base

import io.reactivex.Completable


interface CompletableUseCase<T>:
    UseCase<T, Completable>