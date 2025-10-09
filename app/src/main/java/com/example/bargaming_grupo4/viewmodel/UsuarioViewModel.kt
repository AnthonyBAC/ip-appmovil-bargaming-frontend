package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bargaming_grupo4.model.Rol
import com.example.bargaming_grupo4.model.Usuario
import com.example.bargaming_grupo4.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {
    private val _estado = MutableStateFlow(value = Usuario(usuarioId = 0L, rol = Rol.CLIENTE))
    val estado: StateFlow<Usuario> = _estado

    // Actualiza el campo nombre
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    // Actualiza el campo correo
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correoElectronico = valor, errores = it.errores.copy(correoElectronico = null)) }
    }

    // Actualiza el campo contrasenia
    fun onClaveChange(valor: String) {
        _estado.update { it.copy(password = valor, errores = it.errores.copy(password = null)) }
    }

    // Actualiza el campo dirección
    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    // Actualiza el campo telefono
    fun onTelefonoChange(valor: String) {
        _estado.update { it.copy(telefono = valor, errores = it.errores.copy(telefono = null)) }
    }

    // Validar Login
    fun validarLogin(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            correoElectronico = if (!estadoActual.correoElectronico.contains("@")) "Correo inválido" else null,
            password = if (estadoActual.password.length < 6) "Contraseña incorrecta" else null
        )
        val hayErrores = listOfNotNull(
            errores.correoElectronico,
            errores.password
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // Validar Registro
    fun validarRegistro(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correoElectronico = if (!estadoActual.correoElectronico.contains("@")) "Correo inválido" else null,
            telefono = if (estadoActual.telefono.length < 9) "Teléfono inválido" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null,
            password = if (estadoActual.password.length < 6) "Debe tener al menos 6 caracteres" else null
        )
        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correoElectronico,
            errores.telefono,
            errores.direccion,
            errores.password
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }


}