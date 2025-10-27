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
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.ui.components.AppBottomBar
import com.example.bargaming_grupo4.ui.screens.*
import com.example.bargaming_grupo4.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(initial = false)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
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
                    onAccount = { navController.navigate(Route.AccountEntry.path) },
                    onLogout = {
                        scope.launch { userPrefs.clearSession() }
                        navController.navigate(Route.Home.path) {
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
                        scope.launch { userPrefs.setLoggedIn(true) }
                        navController.navigate(Route.Profile.path) {
                            popUpTo(Route.Login.path) { inclusive = true }
                        }
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

            composable(Route.Profile.path) {
                ProfileScreen(
                    snackbarHostState = snackbarHostState,
                    navController = navController
                )
            }


            composable(Route.AccountEntry.path) {
                AccountEntryPointScreen(navController)
            }
        }
    }
}
