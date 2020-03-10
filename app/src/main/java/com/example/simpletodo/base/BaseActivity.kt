package com.example.simpletodo.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.simpletodo.ui.setting.SettingsFragment
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getThemeConfig()

        AndroidInjection.inject(this)
    }

    protected fun getThemeConfig() {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val themeMode =
            preference.getString(
                SettingsFragment.THEME_KEY,
                SettingsFragment.FOLLOW_SYSTEM.toString()
            )!!
        val current = when (themeMode.toInt()) {
            SettingsFragment.FOLLOW_BATTERY ->
                AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            SettingsFragment.FOLLOW_SYSTEM ->
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            SettingsFragment.NIGHT ->
                AppCompatDelegate.MODE_NIGHT_YES
            SettingsFragment.LIGHT ->
                AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        if (current != AppCompatDelegate.getDefaultNightMode()) {
            Log.e("###", "Set theme for activity ${current}")
            AppCompatDelegate.setDefaultNightMode(current)
        }
    }
}