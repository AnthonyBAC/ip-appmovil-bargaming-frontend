package com.example.bargaming_grupo4.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.local.user.UserDao
import com.example.bargaming_grupo4.model.RegisterRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDao: UserDao
) : ViewModel() {


    // Estados
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _registerOk = MutableStateFlow(false)
    val registerOk = _registerOk.asStateFlow()

    var registerState by mutableStateOf<String?>(null)
        private set

    // Validacion User Name
    private fun validateUserName(userName: String): String? {
        if (userName.isBlank()) return "El usuario es obligatorio"
        val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
        return if (!regex.matches(userName)) "Solo letras y espacios" else null
    }


    // Validacion Email
    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El email es obligatorio"
        val emailPat = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return if (!emailPat) "Email invalido" else null
    }

    // Validacion contraseña
    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "La contraseña es obligatoria"
        if (password.length < 12) return "Minimo de 12 caracteres"
        if (!password.any { it.isUpperCase() }) return "Debe incluir almenos una mayuscula"
        if (!password.any { it.isLowerCase() }) return "Debe incluir alemenos una minuscula"
        if (!password.any { it.isDigit() }) return "Debe incluir almenos un numero"
        if (!password.any { it.isLetterOrDigit() }) return "Debe incluir almenos un simbolo"
        if (password.contains(" ")) return "No debe contener espacios"
        return null
    }

    // Confirmacion contraseña
    private fun confirmationPassword(password: String, confirm: String): String? {
        if (confirm.isBlank()) return "Debes confirmar tu contraseña"
        return if (password != confirm) "Las contraseñas no coinciden" else null
    }


    // Funcion Principal
    fun registerUser(request: RegisterRequest) {

        // username
        validateUserName(request.username)?.let {
            _errorMessage.value = it
            return
        }

        //email
        validateEmail(request.email)?.let {
            _errorMessage.value = it
            return
        }

        //contraseña
        validatePassword(request.password)?.let {
            _errorMessage.value = it
            return
        }

        // confirmacion contraseña
        confirmationPassword(request.password, request.confirmPassword)?.let {
            _errorMessage.value = it
            return
        }

        // validaciones completas
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _registerOk.value = false

            try {
                val response = RetrofitClient.authService.registerUser(request)
                if (response.isSuccessful) {
                    _registerOk.value = true
                } else {
                    saveUserLocally(request)

                }

            } catch (ex: Exception) {
                saveUserLocally(request)
            }
        }

    }

    //Guarda localmente
    private suspend fun saveUserLocally(request: RegisterRequest) {
        val localUser = com.example.bargaming_grupo4.data.local.user.UsersEntity(
            username = request.username,
            email = request.email,
            phone = "",
            direccion = "",
            password = request.password
        )

        userDao.insertUser(localUser)

        _errorMessage.value = "Usuario guardado localmente"
        _registerOk.value = true
        _isLoading.value = false
    }
}