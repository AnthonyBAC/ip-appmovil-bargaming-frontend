package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bargaming_grupo4.data.local.storage.UserPreferences

class LoginViewModelFactory(
    private val userPrefs: UserPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
