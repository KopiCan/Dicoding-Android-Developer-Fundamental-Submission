package com.dicoding.submission3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingViewModel(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }
}
