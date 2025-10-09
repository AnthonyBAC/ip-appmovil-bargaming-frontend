package com.example.bargaming_grupo4.model

data class DetalleVenta (
    val detalleId: Long,
    val venta: Venta,
    val producto: Producto,
    val cantidad: Int,
    val subtotal: Int
) {
}