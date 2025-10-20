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

    // Guarda el estado del login: "success", "error", etc.
    var loginState by mutableStateOf<String?>(null)
        private set

    // Función que hace el login usando Retrofit
    suspend fun login(context: Context, email: String, password: String) {
        try {
            // Crear el objeto con los datos del usuario
            val request = LoginRequest(email, password)

            // Llamar al backend
            val response = RetrofitClient.instance.login(request)

            // Guardar token en SharedPreferences
            SessionManager.saveSession(context, response.token, response.email, response.role)

            // Cambiar el estado (para redirigir si es exitoso)
            loginState = "success"
        } catch (e: Exception) {
            loginState = "error: ${e.message}"
        }
    }
}
