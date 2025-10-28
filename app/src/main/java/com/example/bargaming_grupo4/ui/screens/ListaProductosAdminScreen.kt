package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bargaming_grupo4.ui.components.AdminItemProducto
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.ProductViewModel

@Composable
fun ListaProductosScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel()
) {
    val error by viewModel.error.collectAsState()
    val productos by viewModel.productos.collectAsState()

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
    ) {
        when {
            error != null -> {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            productos.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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

                    items(productos) { producto ->
                        AdminItemProducto(
                            producto = producto,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}