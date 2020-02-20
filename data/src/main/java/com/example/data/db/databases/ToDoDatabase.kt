package com.example.data.db.databases

import android.content.Context
import androidx.room.*
import com.example.data.db.converters.CalendarConverter
import com.example.data.db.daos.ToDoDao
import com.example.data.db.entities.ToDo

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun getToDoDao(): ToDoDao

    companion object {
        @Volatile
        private var instance: ToDoDatabase? = null

        fun getDatabase(context: Context): ToDoDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ToDoDatabase::class.java,
                            "todo_db"
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }
}