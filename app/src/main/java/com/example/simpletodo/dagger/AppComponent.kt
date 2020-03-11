package com.example.simpletodo.dagger

import android.content.Context
import com.example.simpletodo.dagger.scope.ApplicationScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@ApplicationScope
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<App>{
    @Component.Builder
    interface Builder{
        @BindsInstance fun application(applicationContext: Context): Builder
        fun build(): AppComponent
    }
}