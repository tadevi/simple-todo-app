package com.example.data.db.daos

import androidx.room.*
import com.example.data.db.entities.ToDo
import com.example.domain.entities.ToDoItem
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(toDo: ToDo)

    @Update
    fun update(toDo: ToDo): Int

    @Delete
    fun delete(toDo: ToDo): Int

    @Query("select * from todo_item order by name ASC")
    fun getAllToDos(): Observable<List<ToDo>>

    @Query("select * from todo_item where :name=name limit 1")
    fun findTodoByName(name: String): Observable<ToDo?>
}