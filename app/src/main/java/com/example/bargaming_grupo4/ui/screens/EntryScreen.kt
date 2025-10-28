package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.ui.theme.GradientMain
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun AccountEntryPointScreen(navController: NavController) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }

    var isChecking by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1250)
        val loggedIn = userPrefs.isLoggedIn.first()
        isChecking = false
        if (loggedIn) {
            navController.navigate("profile") {
                popUpTo("account_entry") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("account_entry") { inclusive = true }
            }
        }
    }

    if (isChecking) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GradientMain),
            contentAlignment = Alignment.Center

        ) {
            CircularProgressIndicator()
        }
    }
}
