package com.example.bargaming_grupo4.model

data class Product(
    val productId: Long,
    val nombre: String,
    val marca: String,
    val categoria: String,
    val precio: Int,
    val vendedorId: Long,
    val estado: String,
    val recibeOfertas: Boolean,
    val imageUrl: String?
)