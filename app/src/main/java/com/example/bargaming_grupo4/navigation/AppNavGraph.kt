package com.example.bargaming_grupo4.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bargaming_grupo4.ui.components.AppBottomBar
import com.example.bargaming_grupo4.ui.screens.ConsolasScreen
import com.example.bargaming_grupo4.ui.screens.DescProductoScreen
import com.example.bargaming_grupo4.ui.screens.HomeScreen
import com.example.bargaming_grupo4.ui.screens.LoginScreen
import com.example.bargaming_grupo4.ui.screens.NosotrosScreen
import com.example.bargaming_grupo4.ui.screens.RegisterScreen
import com.example.bargaming_grupo4.ui.screens.WelcomeScreen
import com.example.bargaming_grupo4.utils.SessionManager

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    var isLoggedIn by remember { mutableStateOf(false) }

    // 🔹 Verificar si ya hay un token guardado al iniciar
    LaunchedEffect(Unit) {
        val token = SessionManager.getToken(context)
        if (token != null) {
            isLoggedIn = true
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val goHome: () -> Unit = { navController.navigate(Route.Home.path) }
    val goLogin: () -> Unit = { navController.navigate(Route.Login.path) }
    val goRegister: () -> Unit = { navController.navigate(Route.Register.path) }

    Scaffold(
        bottomBar = {
            if (currentRoute != Route.Welcome.path) {
                AppBottomBar(
                    isLoggedIn = isLoggedIn,
                    onHome = goHome,
                    onLogin = goLogin,
                    onRegister = goRegister,
                    onAccount = {
                        if (isLoggedIn) {
                            // Ya no se usa aquí — se maneja en onLogout
                        } else {
                            navController.navigate(Route.Login.path)
                        }
                    },
                    onLogout = {
                        SessionManager.clearSession(context)
                        isLoggedIn = false
                        navController.navigate(Route.Login.path) {
                            popUpTo(Route.Home.path) { inclusive = true }
                        }
                    }
                )
            }
        }
    )
 { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Welcome.path,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Route.Welcome.path) {
                WelcomeScreen(Modifier.fillMaxSize(), navController)
            }

            composable(Route.Home.path) {
                HomeScreen(navController)
            }

            composable(Route.Login.path) {
                LoginScreen(
                    onLoginOk = {
                        isLoggedIn = true
                        goHome()
                    },
                    onGoRegister = goRegister
                )
            }

            composable(Route.Register.path) {
                RegisterScreen(
                    onRegistered = goLogin,
                    onGoLogin = goLogin
                )
            }

            composable(Route.Nosotros.path) {
                NosotrosScreen()
            }

            composable(Route.Descripcion.path) {
                DescProductoScreen(goHome)
            }

            composable(Route.Consolas.path) {
                ConsolasScreen(goHome)
            }
        }
    }
}
