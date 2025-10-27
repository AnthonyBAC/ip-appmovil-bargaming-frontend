package com.example.bargaming_grupo4.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bargaming_grupo4.data.local.storage.UserPreferences
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.AppTextField
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.LoginViewModel
import com.example.bargaming_grupo4.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginOk: () -> Unit,
    onGoRegister: () -> Unit,
    snackbarHostState: SnackbarHostState

) {
    val context = LocalContext.current
    val userPrefs = remember { UserPreferences(context) }
    val factory = remember { LoginViewModelFactory(userPrefs) }
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    val username by userPrefs.userName.collectAsState(initial = null)

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val loginOk by viewModel.loginOk.collectAsState()
    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(initial = false)

    // Efectos de login o error
    LaunchedEffect(loginOk, errorMessage) {
        if (loginOk) {
            Toast.makeText(context, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
            onLoginOk()
        } else if (errorMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(errorMessage ?: "Error desconocido")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientMain)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo()

            // Campos de texto
            AppTextField(value = email, onValueChange = { email = it }, label = "Correo electrónico")
            Spacer(Modifier.height(12.dp))
            AppTextField(value = contrasenia, onValueChange = { contrasenia = it }, label = "Contraseña", isPassword = true)

            Spacer(Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { viewModel.loginUser(email, contrasenia) },
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Cargando..." else "Login")
                }

                OutlinedButton(onClick = onGoRegister) {
                    Text("Regístrate")
                }
            }

            if (isLoggedIn) {
                Spacer(Modifier.height(30.dp))
                Button(
                    onClick = {
                        Toast.makeText(context, "Sesión activa como ${username ?: "usuario"} ✅", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Text("Sesión activa como ${username ?: "usuario"}")
                }

                Spacer(Modifier.height(10.dp))
                OutlinedButton(onClick = {
                    scope.launch {
                        viewModel.logout()
                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Cerrar sesión")
                }
            }
        }
    }

}
