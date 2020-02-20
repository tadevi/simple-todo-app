package com.example.data.mapper

interface Mapper<T, R> {
    fun mapTo(param: T): R
}