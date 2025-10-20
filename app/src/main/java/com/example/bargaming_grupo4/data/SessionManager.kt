package com.example.bargaming_grupo4.utils

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_TOKEN = "jwt_token"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_ROLE = "user_role"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveSession(context: Context, token: String, email: String, role: String) {
        val prefs = getPrefs(context)
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_EMAIL, email)
            putString(KEY_ROLE, role)
            apply()
        }
    }

    fun getToken(context: Context): String? = getPrefs(context).getString(KEY_TOKEN, null)
    fun getEmail(context: Context): String? = getPrefs(context).getString(KEY_EMAIL, null)
    fun getRole(context: Context): String? = getPrefs(context).getString(KEY_ROLE, null)

    fun clearSession(context: Context) {
        getPrefs(context).edit().clear().apply()
    }
}
