package com.example.simpletodo.ui.home.di

import com.example.simpletodo.di.scopes.ActivityScope
import com.example.simpletodo.ui.home.HomeActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {
    fun inject(activity: HomeActivity)
}