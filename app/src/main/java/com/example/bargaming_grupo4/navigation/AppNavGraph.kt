package com.example.bargaming_grupo4.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bargaming_grupo4.ui.components.AppBottomBar
import com.example.bargaming_grupo4.ui.components.AppDrawer
import com.example.bargaming_grupo4.ui.components.defaultDrawerItems
import com.example.bargaming_grupo4.ui.screens.HomeScreen
import com.example.bargaming_grupo4.ui.screens.LoginScreen
import com.example.bargaming_grupo4.ui.screens.RegisterScreen
import com.example.bargaming_grupo4.ui.screens.WelcomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val goHome: () -> Unit = { navController.navigate(Route.Home.path) }
    val goLogin: () -> Unit = { navController.navigate(Route.Login.path) }
    val goRegister: () -> Unit = { navController.navigate(Route.Register.path) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = null,
                items = defaultDrawerItems(
                    onHome = {
                        scope.launch { drawerState.close() }
                        goHome()
                    },
                    onLogin = {
                        scope.launch { drawerState.close() }
                        goLogin()
                    },
                    onRegister = {
                        scope.launch { drawerState.close() }
                        goRegister()
                    }
                )
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                if (currentRoute != Route.Welcome.path &&
                    currentRoute != Route.Login.path &&
                    currentRoute != Route.Register.path
                ) {
                    AppBottomBar(
                        onHome = goHome,
                        onLogin = goLogin,
                        onRegister = goRegister,
                        onMenuClick = {
                            scope.launch {
                                drawerState.open()
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
                        onGoLogin = goLogin,
                        onGoRegister = goRegister
                    )
                }
                composable(Route.Login.path) {
                    LoginScreen(
                        onLoginOk = goHome,
                        onGoRegister = goRegister
                    )
                }
                composable(Route.Register.path) {
                    RegisterScreen(
                        onRegistered = goLogin,
                        onGoLogin = goLogin
                    )
                }
            }
        }
    }
}