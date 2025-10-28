package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bargaming_grupo4.ui.components.CajaTexto
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.ui.utils.formatearCLP
import com.example.bargaming_grupo4.viewmodel.ProductViewModel

@Composable
fun DescProductoScreen(
    productId: Long,
    navController: NavController,
    onBuy: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val producto by viewModel.productoSeleccionado.collectAsState()

    LaunchedEffect(productId) {
        println("Solicitando producto ID = $productId")
        viewModel.getProductById(productId)
    }

    if (producto == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val product = producto!!

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientMain)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            item {
                Image(
                    painter = rememberAsyncImagePainter(model = product.imageUrl),
                    contentDescription = product.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    Text(
                        text = product.nombre,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                CajaTexto(
                    "Descripción",
                    "Descripción del producto aún no disponible."
                )
            }

            item {
                CajaTexto(
                    "Características",
                    "Características del producto aún no disponibles."
                )
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = formatearCLP(product.precio),
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Yellow,
                        fontSize = 30.sp
                    )
                }
            }

            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onBuy,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Comprar")
                    }
                }
            }
        }
    }
}
