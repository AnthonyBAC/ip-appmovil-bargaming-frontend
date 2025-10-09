package com.example.bargaming_grupo4.model

data class Carrito (
    val carritoId: Long,
    val usuario: Usuario,
    val producto: Producto,
    val cantidad: Int,
    val precioProducto: Int
) {
}