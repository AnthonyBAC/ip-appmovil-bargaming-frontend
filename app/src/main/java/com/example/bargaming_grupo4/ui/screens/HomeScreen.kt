package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bargaming_grupo4.ui.components.SeccionConsolas
import com.example.bargaming_grupo4.ui.components.SeccionNosotros
import com.example.bargaming_grupo4.ui.components.SeccionProductosNuevos
import com.example.bargaming_grupo4.ui.components.SeccionProductosSugeridos
import com.example.bargaming_grupo4.ui.components.SliderProductosNuevos

@Composable
fun HomeScreen(navController: NavController) {
    val bg = MaterialTheme.colorScheme.surfaceVariant

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(bg),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SeccionProductosNuevos()
        }

        item {
            SeccionProductosSugeridos()
        }

        item {
            SeccionConsolas()
        }

        item {
            SliderProductosNuevos()
        }

        item {
            SeccionNosotros(navController)
        }
    }
}