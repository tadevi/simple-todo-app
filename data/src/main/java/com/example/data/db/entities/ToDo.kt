package com.example.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "todo_item")
data class ToDo(
    val name: String,
    val description: String,
    val datetime: Calendar? = null,
    val isFinish: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}