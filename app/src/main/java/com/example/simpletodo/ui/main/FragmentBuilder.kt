package com.example.simpletodo.ui.main

import com.example.simpletodo.dagger.scope.FragmentScope
import com.example.simpletodo.dagger.scope.ViewModelScope
import com.example.simpletodo.ui.addToDo.AddTodoFragment
import com.example.simpletodo.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun addTodoFragment(): AddTodoFragment
}