package com.example.domain.usecases.base

interface UseCase<T, R> {
    fun executeUseCase(param: T): R
}