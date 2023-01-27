package com.dicoding.submission3

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    fun getTheme(): Flow<Boolean>{
        return dataStore.data.map { preferences -> preferences[THEME_KEY]  ?: false}
    }

    suspend fun saveTheme(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        private val THEME_KEY = booleanPreferencesKey("Theme_Settings")

        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) : SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val temporalInstance = SettingPreferences(dataStore)
                INSTANCE = temporalInstance
                temporalInstance
            }
        }
    }
}
