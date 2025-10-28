package com.example.bargaming_grupo4.ui.screens

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // 🔹 Funciones locales de validación
    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El correo es obligatorio"
        val emailPat = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        return if (!emailPat) "Correo inválido" else null
    }

    fun validatePassword(password: String): String? =
        if (password.isBlank()) "La contraseña es obligatoria" else null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientMain)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                            // 🔹 Primero validamos antes de llamar al ViewModel
                            val emailError = validateEmail(email)
                            val passwordError = validatePassword(contrasenia)

                            when {
                                emailError != null -> scope.launch {
                                    snackbarHostState.showSnackbar(emailError)
                                }

                                passwordError != null -> scope.launch {
                                    snackbarHostState.showSnackbar(passwordError)
                                }

                                else -> viewModel.ejecutarLogin(
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
                            }
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
