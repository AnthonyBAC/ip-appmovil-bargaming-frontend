package com.example.bargaming_grupo4.ui.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.bargaming_grupo4.navigation.AppNavGraph

@Composable
fun CompactApp() {
    val navController = rememberNavController()

    AppNavGraph(navController = navController)
}
