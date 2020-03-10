package com.example.simpletodo.dagger

import android.content.Context
import com.example.data.db.daos.ToDoDao
import com.example.data.db.databases.ToDoDatabase
import com.example.data.repositories.ToDoRepositoryImpl
import com.example.domain.repositories.ToDoRepository
import com.example.simpletodo.dagger.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
object AppModule {
    @Provides
    @ApplicationScope
    @JvmStatic
    fun provideDatabase(context: Context): ToDoDatabase {
        return ToDoDatabase.getDatabase(context)
    }

    @Provides
    @ApplicationScope
    @JvmStatic
    fun provideDao(database: ToDoDatabase): ToDoDao {
        return database.getToDoDao()
    }

    @Provides
    @ApplicationScope
    @JvmStatic
    fun provideRepository(dao: ToDoDao): ToDoRepository {
        return ToDoRepositoryImpl(dao)
    }
}