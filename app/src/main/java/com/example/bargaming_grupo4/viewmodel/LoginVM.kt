package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    private val userPrefs: UserPreferences
) : ViewModel() {

    // Estados de la UI
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _loginOk = MutableStateFlow(false)
    val loginOk = _loginOk.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _loginOk.value = false

            try {
                // Crear solicitud
                val request = LoginRequest(email, password)
                val response = RetrofitClient.authService.login(request)

                userPrefs.saveToken(response.token)
                userPrefs.setLoggedIn(true)
                _loginOk.value = true

            } catch (e: HttpException) {
                // Errores HTTP específicos del backend
                when (e.code()) {
                    400, 401, 403 -> _errorMessage.value = "Correo o contraseña incorrectos"
                    else -> _errorMessage.value = "Error del servidor: ${e.code()}"
                }
            } catch (e: IOException) {
                _errorMessage.value = "No hay conexión con el servidor"
            } catch (e: Exception) {
                _errorMessage.value = "Error inesperado: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Cerrar sesión
    fun logout() {
        viewModelScope.launch {
            userPrefs.clearSession()
            _loginOk.value = false
        }
    }
}
