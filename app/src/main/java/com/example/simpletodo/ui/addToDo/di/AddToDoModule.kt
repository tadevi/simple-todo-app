package com.example.simpletodo.ui.addToDo.di

import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.InsertToDoItemUseCase
import com.example.domain.usecases.UpdateToDoItemUseCase
import com.example.simpletodo.di.scopes.ActivityScope
import com.example.simpletodo.ui.addToDo.AddToDoPresenter
import dagger.Module
import dagger.Provides

@Module
object AddToDoModule {
    @Provides
    @ActivityScope
    @JvmStatic
    fun provideUpdateTodoItemUseCase(repository: ToDoRepository): UpdateToDoItemUseCase {
        return UpdateToDoItemUseCase(repository)
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
    fun provideAddToDoPresenter(
        insertToDoItemUseCase: InsertToDoItemUseCase,
        updateToDoItemUseCase: UpdateToDoItemUseCase
    ): AddToDoPresenter {
        return AddToDoPresenter(updateToDoItemUseCase, insertToDoItemUseCase)
    }
}