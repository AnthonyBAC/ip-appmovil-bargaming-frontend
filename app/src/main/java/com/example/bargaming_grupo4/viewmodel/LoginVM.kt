package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
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
                userPrefs.saveUserName(response.username)
                userPrefs.setLoggedIn(true)
                _loginOk.value = true



            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val message = try {
                    JSONObject(errorBody ?: "{}").optString("message", "Error desconocido")
                } catch (jsonEx: Exception) {
                    "Error del servidor (${e.code()})"
                }

                _errorMessage.value = message
            }
            catch (e: IOException) {
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
