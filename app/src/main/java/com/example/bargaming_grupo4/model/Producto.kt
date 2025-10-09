package com.example.bargaming_grupo4.model

data class Producto (
    val productoId: Long,
    val nombre: String = "",
    val marca: String = "",
    val categoria: String = "",
    val estado: Estado,
    val recibeOfertas: Boolean = false,
    val errores: ProductoErrores = ProductoErrores()
)
enum class Estado {
    VENDIDO, DISPONIBLE
} // Se crea clase enum para los distintos estados del producto