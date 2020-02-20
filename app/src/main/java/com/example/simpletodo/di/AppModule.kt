package com.example.simpletodo.di

import android.content.Context
import com.example.data.db.daos.ToDoDao
import com.example.data.db.databases.ToDoDatabase
import com.example.data.repositories.ToDoRepositoryImpl
import com.example.domain.repositories.ToDoRepository
import com.example.simpletodo.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @ApplicationScope
    fun provideContext(): Context = context.applicationContext

    @Provides
    @ApplicationScope
    fun provideTodoDatabase(context: Context): ToDoDatabase {
        return ToDoDatabase.getDatabase(context)
    }

    @Provides
    @ApplicationScope
    fun provideDao(database: ToDoDatabase): ToDoDao {
        return database.getToDoDao()
    }

    @Provides
    @ApplicationScope
    fun provideRepository(toDoDao: ToDoDao): ToDoRepository {
        return ToDoRepositoryImpl(toDoDao)
    }
}