package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bargaming_grupo4.R
import com.example.bargaming_grupo4.navigation.Route
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.CajaProducto
import com.example.bargaming_grupo4.ui.components.CajaLista3Productos
import com.example.bargaming_grupo4.ui.components.Slider3Productos
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.ProductViewModel

@Composable
fun HomeScreen(viewModel: ProductViewModel = viewModel(), navController: NavController) {
    val productos by viewModel.productos.collectAsState()
    val error by viewModel.error.collectAsState()

    val productoNuevo = productos.filter { it.productId == 5L }
    val productoConsolas = productos.filter { it.categoria == "Consolas"}

    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    if (error != null) {
        Text(
            text = "Error: $error"
        )
    } else {

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
                items(productoNuevo) { producto ->
                    CajaProducto(
                        titulo1 = "Computadoras",
                        product = producto,
                        navController = navController,
                        route = Route.Descripcion.path,
                        buttonText = "Ver Detalles"
                    )
                }

                item {
                    CajaLista3Productos(
                        "Productos Sugeridos",
                        "Joystick",
                        "Monitor 144",
                        "Notebook",
                        R.drawable.joystick,
                        R.drawable.monitor144,
                        R.drawable.notebook,
                        "Joystick inalámbrico XBOX",
                        "Monitor Gaming 144HZ 1MS",
                        "Notebook Gamer I9-9900K / NVIDIA 4090"
                    )
                }

                items(productoConsolas) { producto ->
                    CajaProducto(
                        titulo1 = "Consolas",
                        product = producto,
                        navController = navController,
                        route = Route.Consolas.path,
                        buttonText = "Ver Detalles"
                    )
                }

                item {
                    Slider3Productos(
                        R.drawable.ryzen5600,
                        R.drawable.i9_14900kf,
                        R.drawable.pc_gamer
                    )
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
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
                            text = "Nosotros",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
