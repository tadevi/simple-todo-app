package com.example.data.mapper

import com.example.data.db.entities.ToDo
import com.example.domain.entities.ToDoItem

object TodoMapper : Mapper<ToDoItem, ToDo> {
    override fun mapTo(param: ToDoItem): ToDo {
        return ToDo(
            param.name,
            param.description,
            param.datetime
        )
    }
}
object ToDoItemMapper : Mapper<ToDo, ToDoItem> {
    override fun mapTo(param: ToDo): ToDoItem {
        return ToDoItem(
            param.name,
            param.description,
            param.datetime
        )
    }
}