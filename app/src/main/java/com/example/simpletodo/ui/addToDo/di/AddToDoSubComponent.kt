package com.example.simpletodo.ui.addToDo.di

import com.example.simpletodo.di.scopes.ActivityScope
import com.example.simpletodo.ui.addToDo.AddToDoActivity
import dagger.Subcomponent

@Subcomponent(modules = [AddToDoModule::class])
@ActivityScope
interface AddToDoSubComponent {
    fun inject(activity: AddToDoActivity)
}