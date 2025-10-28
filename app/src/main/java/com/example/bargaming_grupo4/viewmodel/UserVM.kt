package com.example.bargaming_grupo4.viewmodel

import com.example.bargaming_grupo4.data.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserVM : ViewModel() {

    private val repository = UserRepository()

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun getAllUsuarios(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllUsers(token)
                if (response.isSuccessful) {
                    println("Respuesta correcta")
                    _usuarios.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }
}
