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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bargaming_grupo4.R
import com.example.bargaming_grupo4.navigation.Route
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.CajaProducto
import com.example.bargaming_grupo4.ui.components.CajaLista3Productos
import com.example.bargaming_grupo4.ui.components.Slider3Productos
import com.example.bargaming_grupo4.ui.theme.GradientMain

@Composable
fun HomeScreen(navController: NavController) {
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
                CajaProducto(
                    imagen = R.drawable.pc_gamer,
                    contentDesc = "PC Gamer",
                    titulo1 = "Productos Nuevos",
                    titulo2 = "Computadoras",
                    route = Route.Descripcion.path,
                    buttonText = "Ver más",
                    navController = navController
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

            item {
                CajaProducto(
                    imagen = R.drawable.playstation5,
                    contentDesc = "Playstation 5",
                    titulo1 = "¡Revisa lo mejor en Consolas!",
                    titulo2 = "",
                    buttonText = "Ver más Consolas",
                    route = Route.Consolas.path,
                    navController = navController
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
                }
            }
        }
    }
}
