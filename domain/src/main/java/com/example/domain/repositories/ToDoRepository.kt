package com.example.domain.repositories

import com.example.domain.entities.ToDoItem
import io.reactivex.Completable
import io.reactivex.Observable

interface ToDoRepository {
    fun updateToDoItem(toDoItem: ToDoItem): Observable<Int>
    fun insertToDoItem(toDoItem: ToDoItem): Observable<Int>
    fun deleteToDoItem(toDoItem: ToDoItem): Observable<Int>
    fun getToDoList(): Observable<List<ToDoItem>>
}