package com.example.bargaming_grupo4.model

import java.util.Date

data class Oferta (
    val ofertaId: Long,
    val usuario: Usuario,
    val producto: Producto,
    val monto: Int,
    val estado: String,
    val fechaOferta: Date
) {
}