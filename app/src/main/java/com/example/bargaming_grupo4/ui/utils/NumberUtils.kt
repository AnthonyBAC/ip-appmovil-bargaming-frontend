package com.example.bargaming_grupo4.ui.utils

import java.text.NumberFormat
import java.util.Locale

fun formatearCLP(valor: Int): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("es","CL"))
    return formato.format(valor)
}