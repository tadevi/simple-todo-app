package com.example.domain.entities

import java.io.Serializable
import java.util.*


data class ToDoItem(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val datetime: Calendar? = null,
    val isFinish: Boolean = false
) : Serializable