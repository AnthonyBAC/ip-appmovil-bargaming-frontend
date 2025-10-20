package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bargaming_grupo4.R
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.CajaTexto
import com.example.bargaming_grupo4.ui.components.Slider3Productos
import com.example.bargaming_grupo4.ui.theme.GradientMain

@Composable
fun DescProductoScreen(
    /*nombreProducto: String,
    oferta: Int,
    imagen: Int,
    contentDesc: String*/ //Parametros que usaremos mas adelante
    onBuy: () -> Unit
) {
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
                AppLogo(
                    Modifier
                        .size(80.dp)
                        .padding(start = 8.dp)
                )
            }
            item {
                Slider3Productos(
                    R.drawable.pc_gamer,
                    R.drawable.ryzen5600,
                    R.drawable.i9_14900kf
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
                        text = "I9-9900K / RTX 5060 / Gabinete X con 4 ventiladores",
                        Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            item {
                CajaTexto(
                    "Descripción",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                )
            }
            item {
                CajaTexto(
                    "Características",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
                )
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onBuy,
                        Modifier.weight(1f)
                    ) {
                        Text(
                            "Comprar"
                        )
                    }
                }
            }
        }
    }
}