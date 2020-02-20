package com.example.simpletodo.ui.home.di

import com.example.domain.repositories.ToDoRepository
import com.example.domain.usecases.GetToDoListUseCase
import com.example.simpletodo.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    @ActivityScope
    fun provideGetListUseCase(repository: ToDoRepository): GetToDoListUseCase {
        return GetToDoListUseCase(repository)
    }
}