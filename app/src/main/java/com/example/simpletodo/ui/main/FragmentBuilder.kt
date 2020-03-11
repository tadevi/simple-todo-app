package com.example.simpletodo.ui.main

import com.example.simpletodo.dagger.scope.FragmentScope
import com.example.simpletodo.ui.about.AboutFragment
import com.example.simpletodo.ui.addToDo.AddTodoFragment
import com.example.simpletodo.ui.home.HomeFragment
import com.example.simpletodo.ui.setting.SettingsFragment
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

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun aboutFragment(): AboutFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun settingFragment(): SettingsFragment
}