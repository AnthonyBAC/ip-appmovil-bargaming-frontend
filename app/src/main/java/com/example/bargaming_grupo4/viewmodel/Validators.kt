package com.example.bargaming_grupo4.viewmodel

import android.util.Patterns

// ************VALIDADORES QUE SE PODRÍAN USAR MÁS ADELANTE************

//validaciones para el nombre:  no este vacio, solo letras
fun validateNameLettersOnly(nombre: String): String?{
    if(nombre.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(nombre)) "Solo se aceptan letras y espacios" else null
}

//validaciones del correo: formato y no este vacio
fun validateEmail(correoElectronico: String): String?{
    if(correoElectronico.isBlank()) return "El correo es obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(correoElectronico).matches()
    return if(!ok) "Formato de correo Inválido" else null
}

//validacion de teléfono: no vacio, longitud, solo numeros
fun validatePhoneDigitsOnly(telefono:String): String? {
    if(telefono.isBlank()) return "El teléfono es obligatorio"
    if(!telefono.all { it.isDigit() }) return "Solo se aceptan números"
    if(telefono.length !in 8 .. 9) return "Debe contener entre 8 y 9 digitos"
    return null
}

//validaciones para la seguridad de la contraseña
fun validateStrongPass(password: String): String? {
    if(password.isBlank()) return "Debes escribir tu contraseña"
    if(password.length < 8) return "Debe tener una logintud de más de 7 caracteres"
    if(!password.any { it.isUpperCase() }) return "Debe contener al menos una mayúscula"
    if(!password.any { it.isDigit() }) return "Debe contener al menos un número"
    if(!password.any { it.isLowerCase() }) return "Debe contener al menos una minúscula"
    if(!password.any { it.isLetterOrDigit() }) return "Debe contener al menos un caracter especial"
    if(password.contains(' ')) return "No puede contener espacios en blanco"
    return null
}

//validar que las claves coincidan
fun validateConfirm(password:String, confirm: String): String?{
    if(confirm.isBlank()) return "Debe confirmar su contraseña"
    return if(password != confirm) "Las contraseñas no son iguales" else null
}