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
    override fun updateToDoItem(toDoItem: ToDoItem): Completable {
        return toDoDao.update(TodoMapper.mapTo(toDoItem))
    }

    override fun insertToDoItem(toDoItem: ToDoItem): Completable {
        return toDoDao.insert(TodoMapper.mapTo(toDoItem))
    }

    override fun deleteToDoItem(toDoItem: ToDoItem): Completable {
        return toDoDao.delete(TodoMapper.mapTo(toDoItem))
    }

    override fun getToDoList(): Observable<List<ToDoItem>> {
        return toDoDao.getAllToDos().map {
            it.map { item->ToDoItemMapper.mapTo(item) }
        }
    }
}