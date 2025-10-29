package com.example.bargaming_grupo4.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.ui.components.AppBottomBar
import com.example.bargaming_grupo4.ui.screens.AccountEntryPointScreen
import com.example.bargaming_grupo4.ui.screens.DescProductoScreen
import com.example.bargaming_grupo4.ui.screens.HomeScreen
import com.example.bargaming_grupo4.ui.screens.ListaProductosScreen
import com.example.bargaming_grupo4.ui.screens.ListaUsuariosAdminScreen
import com.example.bargaming_grupo4.ui.screens.LoginScreen
import com.example.bargaming_grupo4.ui.screens.NosotrosScreen
import com.example.bargaming_grupo4.ui.screens.ProfileScreen
import com.example.bargaming_grupo4.ui.screens.RegisterScreen
import com.example.bargaming_grupo4.ui.screens.WelcomeScreen
import com.example.bargaming_grupo4.utils.SessionManager
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
    val goRegister: () -> Unit = { navController.navigate(Route.AdminProductList.path) }

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
                    onGoRegister = goRegister,
                    snackbarHostState = snackbarHostState,
                    navController = navController
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

            composable(Route.AdminProductList.path) {
                ListaProductosScreen(
                    navController
                )
            }

            composable(Route.AdminUsersList.path) {
                val token = SessionManager.getToken(context) ?: ""

                ListaUsuariosAdminScreen(
                    navController = navController,
                    token = token
                )
            }

        }
    }
}
