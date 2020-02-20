package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_item")
data class ToDo(
    @PrimaryKey
    val name: String,
    val description: String,
    val datetime: Calendar? = null
)