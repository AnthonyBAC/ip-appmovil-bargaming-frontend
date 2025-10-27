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
import androidx.navigation.NavController
import com.example.bargaming_grupo4.model.Product
import com.example.bargaming_grupo4.navigation.Route

@Composable
fun CajaLista3Productos(
    title: String,
    productos: List<Product>,
    navController: NavController
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            productos.take(3).forEach { producto ->
                ProductoCard(
                    nombre = producto.nombre,
                    imagenUrl = producto.imageUrl,
                    desc = producto.categoria,
                    onClick = { navController.navigate("${Route.Descripcion.path}/${producto.productId}") }
                )
            }
        }
    }
}
