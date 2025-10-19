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
import com.example.bargaming_grupo4.R

@Composable
fun SeccionProductosSugeridos() {
    Column {
        Text(
            text = "Productos Sugeridos",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val productos = listOf(
            Pair("Joystick", R.drawable.joystick),
            Pair("Monitor 144", R.drawable.monitor144),
            Pair("Notebook", R.drawable.notebook)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            productos.forEach { (nombre, imagenResId) ->
                ProductoCard(nombre = nombre, imagenResId = imagenResId)
            }
        }
    }
}
