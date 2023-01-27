package com.dicoding.submission3

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel (private val preferences: SettingPreferences): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return preferences.getTheme().asLiveData()
    }

    fun saveTheme(isDarkModeAcitve: Boolean){
        viewModelScope.launch {
            preferences.saveTheme(isDarkModeAcitve)
        }
    }
}
