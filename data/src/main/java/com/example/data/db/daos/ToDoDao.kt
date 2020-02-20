package com.example.data.db.daos

import androidx.room.*
import com.example.data.db.entities.ToDo
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(toDo: ToDo): Completable

    @Update
    fun update(toDo: ToDo): Completable

    @Delete
    fun delete(toDo: ToDo): Completable

    @Query("select * from todo_item")
    fun getAllToDos(): Observable<List<ToDo>>
}