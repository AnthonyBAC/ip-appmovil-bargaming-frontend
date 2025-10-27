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
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.AppTextField
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginOk: () -> Unit,   // Acción para ir a Home si el login fue correcto
    onGoRegister: () -> Unit // Acción para ir a Registro
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel()
    val scope = rememberCoroutineScope()

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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AppLogo()

            AppTextField(
                value = email,
                onValueChange = { email = it },
                label = "Correo electrónico"
            )

            Spacer(Modifier.height(12.dp))

            AppTextField(
                value = contrasenia,
                onValueChange = { contrasenia = it },
                label = "Contraseña"
            )

            Spacer(Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        // Llamar al backend
                        scope.launch {
                            isLoading = true
                            viewModel.login(context, email, contrasenia)
                            isLoading = false

                            if (viewModel.loginState == "success") {
                                Toast.makeText(context, "Login correcto", Toast.LENGTH_SHORT).show()
                                onLoginOk()
                            } else {
                                Toast.makeText(context, "Error: ${viewModel.loginState}", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Cargando..." else "Login")
                }

                OutlinedButton(onClick = onGoRegister) {
                    Text("Registrate")
                }
            }
        }
    }
}
