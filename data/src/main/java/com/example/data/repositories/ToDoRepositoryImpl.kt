package com.example.data.repositories

import com.example.data.db.daos.ToDoDao
import com.example.data.mapper.ToDoItemMapper
import com.example.data.mapper.TodoMapper
import com.example.domain.entities.ToDoItem
import com.example.domain.repositories.ToDoRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(private val toDoDao: ToDoDao) : ToDoRepository {
    override fun updateToDoItem(toDoItem: ToDoItem): Observable<Int> {
        return Observable.defer {
            toDoDao.update(TodoMapper.mapTo(toDoItem))
            Observable.just(1)
        }
    }

    override fun insertToDoItem(toDoItem: ToDoItem): Observable<Int> {
        return Observable.fromCallable {
            toDoDao.insert(TodoMapper.mapTo(toDoItem))
            1
        }
    }

    override fun deleteToDoItem(toDoItem: ToDoItem): Observable<Int> {
        return Observable.fromCallable {
            toDoDao.delete(TodoMapper.mapTo(toDoItem))
            1
        }
    }

    override fun getToDoList(): Observable<List<ToDoItem>> {
        return toDoDao.getAllToDos().map {
            it.map { item -> ToDoItemMapper.mapTo(item) }
        }
    }
}