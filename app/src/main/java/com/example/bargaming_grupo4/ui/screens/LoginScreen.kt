package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.AppTextField
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.LoginViewModel
import com.example.bargaming_grupo4.viewmodel.LoginViewModelFactory

@Composable
fun LoginScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    onGoRegister: () -> Unit
) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val factory = remember { LoginViewModelFactory(userPrefs) }
    val viewModel: LoginViewModel = viewModel(factory = factory)
    rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientMain)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AppLogo()
                Spacer(Modifier.height(12.dp))

                AppTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo electrónico"
                )

                Spacer(Modifier.height(12.dp))

                AppTextField(
                    value = contrasenia,
                    onValueChange = { contrasenia = it },
                    label = "Contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            viewModel.ejecutarLogin(
                                email = email,
                                password = contrasenia,
                                setLoading = { isLoading = it },
                                showSuccess = {
                                    snackbarHostState.showSnackbar("Inicio de sesión correcto")
                                },
                                navigateToProfile = {
                                    navController.navigate("profile") {
                                        popUpTo("account_entry") { inclusive = true }
                                    }
                                },
                                showError = { msg ->
                                    snackbarHostState.showSnackbar(msg)
                                }
                            )
                        },
                        enabled = !isLoading
                    ) {
                        Text(if (isLoading) "Cargando..." else "Login")
                    }

                    OutlinedButton(onClick = onGoRegister, enabled = !isLoading) {
                        Text("Regístrate")
                    }
                }
            }
        }
    }
}
