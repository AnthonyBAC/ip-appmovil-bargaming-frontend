package com.example.bargaming_grupo4.model

import java.util.Date

data class Venta (
    val ventaId: Long,
    val usuario: Usuario,
    val vendedor: Usuario,
    val fecha: Date,
    val total: String,
    val estado: String,
    val tipo: String
)   {
}