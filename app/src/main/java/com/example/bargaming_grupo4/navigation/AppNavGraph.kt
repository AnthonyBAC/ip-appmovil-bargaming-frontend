package com.example.bargaming_grupo4.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bargaming_grupo4.ui.components.AppBottomBar
import com.example.bargaming_grupo4.ui.screens.HomeScreen
import com.example.bargaming_grupo4.ui.screens.LoginScreen
import com.example.bargaming_grupo4.ui.screens.NosotrosScreen
import com.example.bargaming_grupo4.ui.screens.RegisterScreen
import com.example.bargaming_grupo4.ui.screens.WelcomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    var isLoggedIn by remember { mutableStateOf(false) }


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val goHome: () -> Unit = { navController.navigate(Route.Home.path) }
    val goLogin: () -> Unit = { navController.navigate(Route.Login.path) }
    val goRegister: () -> Unit = { navController.navigate(Route.Register.path) }


    Scaffold(
            bottomBar = {
                if (currentRoute != Route.Welcome.path) {
                    AppBottomBar(
                        onHome = goHome,
                        onLogin = goLogin,
                        onRegister = goRegister,
                        onAccount = {
                            if (isLoggedIn) {
                                // TODO: más adelante: ir a la pantalla de cuenta
                                // navController.navigate(Route.Account.path)
                            } else {
                                navController.navigate(Route.Login.path)
                            }
                        }
                    )

                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Route.Welcome.path,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Route.Welcome.path) {
                    WelcomeScreen(Modifier.fillMaxSize(), navController)
                }
                composable(Route.Home.path) {
                    HomeScreen(
                        navController
                    )
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
            }
        }

}