package com.example.simpletodo.ui.addToDo.di

import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.domain.usecases.UpdateToDoItemUseCase
import com.example.simpletodo.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class AddToDoModule {
    @Provides
    @ActivityScope
    fun provideUpdateTodoItemUseCase(repository: ToDoRepository): UpdateToDoItemUseCase {
        return UpdateToDoItemUseCase(repository)
    }

    @Provides
    @ActivityScope
    fun provideInsertTodoItemUseCase(repository: ToDoRepository): InsertToDoItemUseCase {
        return InsertToDoItemUseCase(repository)
    }
}