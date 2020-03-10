package com.example.simpletodo.ui.home

import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.DeleteToDoItemUseCase
import com.example.domain.usecases.GetToDoListUseCase
import com.example.simpletodo.dagger.scope.ApplicationScope
import com.example.simpletodo.dagger.scope.ViewModelScope
import dagger.Module
import dagger.Provides

@Module
object HomeModule {
    @Provides
    @ViewModelScope
    @JvmStatic
    fun provideGetTodoListUseCase(repository: ToDoRepository): GetToDoListUseCase {
        return GetToDoListUseCase((repository))
    }


    @Provides
    @ViewModelScope
    @JvmStatic
    fun provideDeleteTodoItemUseCase(repository: ToDoRepository): DeleteToDoItemUseCase {
        return DeleteToDoItemUseCase((repository))
    }
}