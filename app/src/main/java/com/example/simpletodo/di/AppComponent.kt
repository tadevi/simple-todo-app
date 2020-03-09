package com.example.simpletodo.di

import android.app.Application
import androidx.work.Worker
import com.example.simpletodo.di.scopes.ApplicationScope
import com.example.simpletodo.ui.addToDo.di.AddToDoSubComponent
import com.example.simpletodo.ui.home.di.HomeSubComponent
import dagger.Component

@ApplicationScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)
    fun inject(receiver: Worker)

    fun homeActivity(): HomeSubComponent
    fun addToDoActivity(): AddToDoSubComponent
}