package com.example.bargaming_grupo4.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bargaming_grupo4.viewmodel.UsuarioViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

        Column(
            Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            // Campo correo
            OutlinedTextField(
                value = estado.correoElectronico,
                onValueChange = viewModel::onCorreoChange,
                label = { Text(text = "Correo electrónico") },
                isError = estado.errores.correoElectronico != null,
                supportingText = {
                    estado.errores.correoElectronico?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            // Campo clave
            OutlinedTextField(
                value = estado.password,
                onValueChange = viewModel::onClaveChange,
                label = { Text(text = "Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.password != null,
                supportingText = {
                    estado.errores.password?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón: Inicio de sesión
            Button(
                onClick = {
                    if (viewModel.validarLogin()) {
                        navController.navigate(route = "home")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Iniciar Sesión")
            }
            // Botón: Registrarse
            Button(
                onClick = {
                    navController.navigate(route = "registro")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Registrarse")
            }
        }
}