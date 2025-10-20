package com.example.bargaming_grupo4.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CajaLista3Productos(
    title: String,
    producto1: String,
    producto2: String,
    producto3: String,
    imagenProducto1: Int,
    imagenProducto2: Int,
    imagenProducto3: Int,
    desc1: String,
    desc2: String,
    desc3: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val productos = listOf(
            Triple(producto1, imagenProducto1, desc1),
            Triple(producto2, imagenProducto2, desc2),
            Triple(producto3, imagenProducto3, desc3)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            productos.forEach { (nombre, imagenResId, desc) ->
                ProductoCard(nombre = nombre, imagenResId = imagenResId, desc = desc)
            }
        }
    }
}
