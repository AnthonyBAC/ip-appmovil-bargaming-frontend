package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(
    private val userPrefs: UserPreferences
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _loginOk = MutableStateFlow(false)
    val loginOk = _loginOk.asStateFlow()

    private fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        _isLoading.value = true
        _errorMessage.value = null
        _loginOk.value = false

        return try {
            val request = LoginRequest(email, password)
            val response = RetrofitClient.authService.login(request)

            // Guardamos sesión local
            userPrefs.saveToken(response.token)
            userPrefs.saveUserName(response.username)
            userPrefs.setLoggedIn(true)

            _loginOk.value = true
            true
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = try {
                JSONObject(errorBody ?: "{}").optString("message", "Error desconocido")
            } catch (_: Exception) {
                "Error del servidor (${e.code()})"
            }

            _errorMessage.value = message
            false
        } catch (e: IOException) {
            _errorMessage.value = "No hay conexión con el servidor"
            false
        } catch (e: Exception) {
            _errorMessage.value = "Error inesperado: ${e.localizedMessage}"
            false
        } finally {
            _isLoading.value = false
        }
    }

    fun ejecutarLogin(
        email: String,
        password: String,
        setLoading: (Boolean) -> Unit,
        showSuccess: suspend () -> Unit,
        navigateToProfile: () -> Unit,
        showError: suspend (String) -> Unit
    ) {
        viewModelScope.launch {
            // activar loader
            setLoading(true)

            val ok = loginUser(email, password)

            if (ok) {
                showSuccess()
                delay(600)
                navigateToProfile()
            } else {
                showError(errorMessage.value ?: "Error en inicio de sesión")
                setLoading(false)
            }
        }
    }

    fun ejecutarLogout(
        setLoggingOut: (Boolean) -> Unit,
        showSnackbar: suspend (String) -> Unit,
        navigateToAccount: () -> Unit
    ) {
        viewModelScope.launch {
            // activar loader
            setLoggingOut(true)
            showSnackbar("Sesión cerrada correctamente...")
            logout()
            delay(400)
            navigateToAccount()
        }


    }

    fun logout() {
        viewModelScope.launch {
            userPrefs.clearSession()
            _loginOk.value = false
        }
    }


}
