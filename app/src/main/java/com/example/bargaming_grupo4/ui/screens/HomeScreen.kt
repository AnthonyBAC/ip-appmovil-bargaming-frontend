package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bargaming_grupo4.navigation.Route
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.CajaProducto
import com.example.bargaming_grupo4.ui.components.CajaLista3Productos
import com.example.bargaming_grupo4.ui.components.Slider3Productos
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.ProductViewModel

@Composable
fun HomeScreen(
    viewModel: ProductViewModel = viewModel(),
    navController: NavController
) {
    val productos by viewModel.productos.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = true)
    val isExtraLoading by viewModel.extraLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.mostrarLoaderInicial()
    }
    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientMain)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isExtraLoading || isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Cargando productos...",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else if (error != null) {
            // error
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Error al cargar productos:\n$error",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        } else {
            // contenido principal
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val productoNuevo = productos.filter { it.productId == 5L }
                    val productoConsolas = productos.filter { it.categoria == "Consolas" }

                    items(productoNuevo) { producto ->
                        CajaProducto(
                            titulo1 = "Computadoras",
                            product = producto,
                            navController = navController,
                            buttonText = "Ver Detalles"
                        )
                    }

                    item {
                        CajaLista3Productos(
                            "Productos Sugeridos",
                            productos,
                            navController
                        )
                    }

                    items(productoConsolas) { producto ->
                        CajaProducto(
                            titulo1 = "Consolas",
                            product = producto,
                            navController = navController,
                            buttonText = "Ver Detalles"
                        )
                    }

                    item {
                        Slider3Productos(
                            productos,
                            navController
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AppLogo(
                                Modifier
                                    .size(80.dp)
                                    .padding(top = 8.dp)
                                    .clickable { navController.navigate(Route.Nosotros.path) }
                            )
                            Text(
                                text = "",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
