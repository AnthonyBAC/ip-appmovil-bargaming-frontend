package com.example.bargaming_grupo4.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.local.database.AppDataBase
import com.example.bargaming_grupo4.data.local.user.UserDao
import com.example.bargaming_grupo4.model.RegisterRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    application: Application
) : AndroidViewModel(application) {

    val userDao: UserDao = AppDataBase.getInstance(application.applicationContext).userDao()
    // Estados principales
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _registerOk = MutableStateFlow(false)
    val registerOk = _registerOk.asStateFlow()

    // errores persistentes
    var userNameError by mutableStateOf<String?>(null)
        private set
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    // validaciones
    private fun validateUserName(userName: String): String? {
        if (userName.isBlank()) return "El nombre es obligatorio"
        val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
        return if (!regex.matches(userName)) "Solo letras y espacios" else null
    }

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        val emailPat = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return if (!emailPat) "Correo inválido" else null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "La contraseña es obligatoria"
        if (password.length < 12) return "Mínimo de 12 caracteres"
        if (!password.any { it.isUpperCase() }) return "Debe incluir al menos una mayúscula"
        if (!password.any { it.isLowerCase() }) return "Debe incluir al menos una minúscula"
        if (!password.any { it.isDigit() }) return "Debe incluir al menos un número"
        if (!password.any { !it.isLetterOrDigit() }) return "Debe incluir al menos un símbolo"
        if (password.contains(" ")) return "No debe contener espacios"
        return null
    }

    private fun confirmationPassword(password: String, confirm: String): String? {
        if (confirm.isBlank()) return "Debes confirmar tu contraseña"
        return if (password != confirm) "Las contraseñas no coinciden" else null
    }

    // validaciones persistente
    fun onUserNameChange(value: String) {
        userNameError = validateUserName(value)
    }

    fun onEmailChange(value: String) {
        emailError = validateEmail(value)
    }

    fun onPasswordChange(value: String) {
        passwordError = validatePassword(value)
    }

    fun onConfirmPasswordChange(password: String, confirm: String) {
        confirmPasswordError = confirmationPassword(password, confirm)
    }

    // Registro
    fun registerUser(request: RegisterRequest) {

        val nameErr = validateUserName(request.username)
        val emailErr = validateEmail(request.email)
        val passErr = validatePassword(request.password)
        val confirmErr = confirmationPassword(request.password, request.confirmPassword)

        userNameError = nameErr
        emailError = emailErr
        passwordError = passErr
        confirmPasswordError = confirmErr

        if (listOf(nameErr, emailErr, passErr, confirmErr).any { it != null }) {
            _errorMessage.value = "Por favor rellena los campos necesarios"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _registerOk.value = false

            try {
                val response = RetrofitClient.authService.registerUser(request)

                if (response.isSuccessful) {
                    _registerOk.value = true
                } else {
                    _errorMessage.value = "Error en el registro (${response.code()})"
                    saveUserLocally(request)
                }

            } catch (ex: Exception) {
                saveUserLocally(request)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Local sqlite
    private suspend fun saveUserLocally(request: RegisterRequest) {
        val localUser = com.example.bargaming_grupo4.data.local.user.UsersEntity(
            username = request.username,
            email = request.email,
            phone = "",
            direccion = "",
            password = request.password
        )
        userDao.insertUser(localUser)
    }
}
