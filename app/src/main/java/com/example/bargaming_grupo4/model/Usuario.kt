package com.example.bargaming_grupo4.model

data class Usuario (
    val usuarioId: Long,
    val nombre: String = "",
    val correoElectronico: String = "",
    val direccion: String = "",
    val telefono: String = "", //Telefono se queda como String para facilitar las validaciones (convertirlo a .toInt para BD)
    val password: String = "",
    val rol: Rol,
    val errores: UsuarioErrores = UsuarioErrores() // Objeto que contiene los errores por campo
)
enum class Rol {
               CLIENTE, VENDEDOR, ADMIN
} // Se crea clase enum para los distintos roles del usuario