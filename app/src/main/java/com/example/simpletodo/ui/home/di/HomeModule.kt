package com.example.simpletodo.ui.home.di

import android.util.Log
import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.DeleteToDoItemUseCase
import com.example.domain.usecases.GetToDoListUseCase
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.simpletodo.di.scopes.ActivityScope
import com.example.simpletodo.ui.home.HomePresenter
import dagger.Module
import dagger.Provides
import org.jetbrains.annotations.Contract

@Module
object HomeModule {
    @Provides
    @ActivityScope
    @JvmStatic
    fun provideGetListUseCase(repository: ToDoRepository): GetToDoListUseCase {
        return GetToDoListUseCase(repository)
    }

    @Provides
    @ActivityScope
    @JvmStatic
    fun provideDeleteTodoItemUseCase(repository: ToDoRepository): DeleteToDoItemUseCase {
        return DeleteToDoItemUseCase(repository)
    }

    @Provides
    @ActivityScope
    @JvmStatic
    fun provideInsertTodoItemUseCase(repository: ToDoRepository): InsertToDoItemUseCase {
        return InsertToDoItemUseCase(repository)
    }

    @Provides
    @ActivityScope
    @JvmStatic
    fun providePresenter(
        getToDoListUseCase: GetToDoListUseCase,
        deleteToDoItemUseCase: DeleteToDoItemUseCase,
        insertToDoItemUseCase: InsertToDoItemUseCase
    ): HomePresenter {
        Log.e("####","Provide presenter")
        return HomePresenter(getToDoListUseCase, deleteToDoItemUseCase, insertToDoItemUseCase)
    }
}