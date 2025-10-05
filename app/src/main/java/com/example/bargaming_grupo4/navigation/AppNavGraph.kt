package com.example.bargaming_grupo4.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bargaming_grupo4.ui.screens.WelcomeScreen
import com.example.bargaming_grupo4.ui.screens.HomeScreen
import androidx.compose.ui.Modifier
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
        composable("home") {
            HomeScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
