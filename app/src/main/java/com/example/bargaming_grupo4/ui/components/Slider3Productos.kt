package com.example.bargaming_grupo4.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bargaming_grupo4.model.Product
import com.example.bargaming_grupo4.navigation.Route

@Composable
fun Slider3Productos(
    productos: List<Product>,
    navController: NavController
) {
    if (productos.isEmpty()) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        LazyRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // cantidad de imagenes a tomar
            items(productos.take(3)) { producto ->
                val imagen = producto.imageUrl ?: ""

                Image(
                    painter = rememberAsyncImagePainter(producto.imageUrl),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(280.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate("${Route.Descripcion.path}/${producto.productId}")
                        }
                )
            }
        }
    }
}
