package com.example.simpletodo.ui.addToDo

import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.domain.usecases.UpdateToDoItemUseCase
import com.example.simpletodo.dagger.scope.ApplicationScope
import com.example.simpletodo.dagger.scope.ViewModelScope
import dagger.Module
import dagger.Provides

@Module
object AddToDoModule {
    @Provides
    @ViewModelScope
    @JvmStatic
    fun provideUpdateItemUseCase(repository: ToDoRepository): UpdateToDoItemUseCase {
        return UpdateToDoItemUseCase((repository))
    }

    @Provides
    @ViewModelScope
    @JvmStatic
    fun provideInsertTodoUseCase(repository: ToDoRepository): InsertToDoItemUseCase {
        return InsertToDoItemUseCase((repository))
    }
}