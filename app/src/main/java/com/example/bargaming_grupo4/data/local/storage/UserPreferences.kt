package com.example.bargaming_grupo4.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {

    private val usernameKey = stringPreferencesKey("user_name")
    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")
    private val tokenKey = stringPreferencesKey("jwt_token")

    suspend fun setLoggedIn(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[isLoggedInKey] = value
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token
        }
    }

    suspend fun saveUserName(username: String) {
        context.dataStore.edit { prefs ->
            prefs[usernameKey] = username
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(isLoggedInKey)
            prefs.remove(tokenKey)
            prefs.remove(usernameKey)
        }
    }

    val userName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[usernameKey]
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[isLoggedInKey] ?: false
    }

    val token: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[tokenKey]
    }

    val profileImageUrl = context.dataStore.data.map { prefs ->
        prefs[stringPreferencesKey("profile_image_url")] ?: ""
    }

    suspend fun saveProfileImageUrl(url: String) {
        context.dataStore.edit { prefs ->
            prefs[stringPreferencesKey("profile_image_url")] = url
        }
    }

}

