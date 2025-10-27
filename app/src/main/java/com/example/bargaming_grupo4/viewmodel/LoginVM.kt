package com.example.bargaming_grupo4.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import com.example.bargaming_grupo4.utils.SessionManager

class LoginViewModel : ViewModel() {

    var loginState by mutableStateOf<String?>(null)
        private set

    suspend fun login(context: Context, email: String, password: String) {
        try {
            val request = LoginRequest(email, password)

            val response = RetrofitClient.authService.login(request)

            SessionManager.saveSession(context, response.token, response.email, response.role)

            loginState = "success"
        } catch (e: Exception) {
            loginState = "error: ${e.message}"
        }
    }
}
