package com.example.domain.usecases

import io.reactivex.Observable

interface BaseUseCase<T : BaseUseCase.BaseRequest, R> {
    interface BaseRequest
    interface BaseResponse

    fun execute(request: T): Observable<R>
}