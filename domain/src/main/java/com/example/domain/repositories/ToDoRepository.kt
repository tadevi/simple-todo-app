package com.example.domain.repositories

import com.example.domain.entities.ToDoItem
import io.reactivex.Completable
import io.reactivex.Observable

interface ToDoRepository {
    fun updateToDoItem(toDoItem: ToDoItem): Completable
    fun insertToDoItem(toDoItem: ToDoItem): Completable
    fun deleteToDoItem(toDoItem: ToDoItem): Completable
    fun getToDoList(): Observable<List<ToDoItem>>
}