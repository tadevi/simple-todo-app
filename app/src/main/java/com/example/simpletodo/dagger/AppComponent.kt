package com.example.simpletodo.dagger

import android.content.Context
import com.example.simpletodo.dagger.scope.ApplicationScope
import com.example.simpletodo.ui.addToDo.AddToDoModule
import com.example.simpletodo.ui.home.HomeModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class, ViewModelModule::class,HomeModule::class,AddToDoModule::class])
interface AppComponent : AndroidInjector<App>{
    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}