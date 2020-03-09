package com.example.data.mapper

import com.example.data.db.entities.ToDo
import com.example.domain.entities.ToDoItem

object TodoMapper : Mapper<ToDoItem, ToDo> {
    override fun mapTo(param: ToDoItem): ToDo {
        val todo = ToDo(
            param.name,
            param.description,
            param.datetime,
            param.isFinish
        )
        todo.id = param.id
        return todo
    }
}

object ToDoItemMapper : Mapper<ToDo, ToDoItem> {
    override fun mapTo(param: ToDo): ToDoItem {
        return ToDoItem(
            param.id,
            param.name,
            param.description,
            param.datetime,
            param.isFinish
        )
    }
}