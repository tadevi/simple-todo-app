package com.example.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ToDoItem(
    val name: String = "",
    val description: String = "",
    val datetime: Calendar? = null
) : Parcelable