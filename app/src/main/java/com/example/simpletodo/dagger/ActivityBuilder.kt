package com.example.simpletodo.dagger

import com.example.simpletodo.dagger.scope.ActivityScope
import com.example.simpletodo.ui.main.FragmentBuilder
import com.example.simpletodo.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity
}