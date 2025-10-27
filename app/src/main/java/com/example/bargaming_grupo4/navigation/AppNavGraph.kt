package com.example.bargaming_grupo4.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bargaming_grupo4.ui.components.AppBottomBar
import com.example.bargaming_grupo4.ui.screens.*
import com.example.bargaming_grupo4.utils.SessionManager
import com.example.bargaming_grupo4.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    var isLoggedIn by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // snackbar global
    val snackbarHostState = remember { SnackbarHostState() }

    // Verificacion de toekn
    LaunchedEffect(Unit) {
        val token = SessionManager.getToken(context)
        isLoggedIn = token != null
    }

    val productViewModel: ProductViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val goHome: () -> Unit = { navController.navigate(Route.Home.path) }
    val goLogin: () -> Unit = { navController.navigate(Route.Login.path) }
    val goRegister: () -> Unit = { navController.navigate(Route.Register.path) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (currentRoute != Route.Welcome.path) {
                AppBottomBar(
                    isLoggedIn = isLoggedIn,
                    onHome = goHome,
                    onLogin = goLogin,
                    onRegister = goRegister,
                    onAccount = {
                        if (isLoggedIn) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Ya estás logueado")
                            }
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Welcome.path,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(Route.Welcome.path) {
                WelcomeScreen(Modifier.fillMaxSize(), navController)
            }

            composable(Route.Home.path) {
                HomeScreen(productViewModel, navController)
            }

            composable(Route.Login.path) {
                LoginScreen(
                    onLoginOk = {
                        isLoggedIn = true
                        goHome()
                    },
                    onGoRegister = goRegister,
                    snackbarHostState = snackbarHostState
                )
            }

            composable(Route.Register.path) {
                RegisterScreen(
                    onRegistered = goLogin,
                    snackbarHostState = snackbarHostState
                )
            }

            composable(Route.Nosotros.path) {
                NosotrosScreen()
            }

            composable("${Route.Descripcion.path}/{productId}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
                if (id != null) {
                    DescProductoScreen(
                        productId = id,
                        navController = navController,
                        onBuy = { navController.navigate(Route.Home.path) }
                    )
                }
            }

        }

    }
}
