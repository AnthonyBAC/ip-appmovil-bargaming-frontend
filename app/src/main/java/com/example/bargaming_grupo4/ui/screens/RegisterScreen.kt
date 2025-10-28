package com.example.bargaming_grupo4.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.bargaming_grupo4.data.local.database.AppDataBase
import com.example.bargaming_grupo4.model.RegisterRequest
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.AppTextField
import com.example.bargaming_grupo4.ui.theme.GradientMain
import com.example.bargaming_grupo4.viewmodel.RegisterViewModel
import com.example.bargaming_grupo4.viewmodel.RegisterViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val userDao = AppDataBase.getInstance(context).userDao()
    val factory = remember { RegisterViewModelFactory(userDao) }
    val viewModel: RegisterViewModel = viewModel(factory = factory)

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val registerOk by viewModel.registerOk.collectAsState()

    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()
    LaunchedEffect(registerOk, errorMessage) {
        if (registerOk) {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
            onRegistered()
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            AppLogo()

            // Nombre
            AppTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    viewModel.onUserNameChange(it)
                },
                label = "Nombre completo"
            )
            viewModel.userNameError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            // Email
            AppTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.onEmailChange(it)
                },
                label = "Correo electrónico"
            )
            viewModel.emailError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            // Contraseña
            AppTextField(
                value = password,
                onValueChange = {
                    password = it
                    viewModel.onPasswordChange(it)
                    viewModel.onConfirmPasswordChange(password, confirmPassword)
                },
                label = "Contraseña",
                isPassword = true
            )
            viewModel.passwordError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            // Confirmar contraseña
            AppTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    viewModel.onConfirmPasswordChange(password, it)
                },
                label = "Confirmar contraseña",
                isPassword = true
            )
            viewModel.confirmPasswordError?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    val request = RegisterRequest(
                        username = nombre,
                        email = email,
                        password = password,
                        confirmPassword = confirmPassword
                    )
                    viewModel.registerUser(request)
                },
                enabled = !isLoading
            ) {
                Text(if (isLoading) "Registrando..." else "Registrarse")
            }
        }
    }
}