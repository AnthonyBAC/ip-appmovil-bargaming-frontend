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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.bargaming_grupo4.model.Usuario
import androidx.compose.ui.unit.dp
import com.example.bargaming_grupo4.ui.components.AppLogo
import com.example.bargaming_grupo4.ui.components.AppTextField
import com.example.bargaming_grupo4.ui.theme.GradientMain

@Composable
fun LoginScreen(
    onLoginOk: () -> Unit,   // Acción para “volver” a Home
    onGoRegister: () -> Unit // Acción para ir a Registro
) {
    val bg = MaterialTheme.colorScheme.secondaryContainer // Fondo distinto para contraste
    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo
            .background(GradientMain) // Fondo
            .padding(16.dp), // Margen
        contentAlignment = Alignment.Center // Centro
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally // Centrado horizontal
        ) {
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
                Button(onClick = onLoginOk) { Text("Login") }
                OutlinedButton(onClick = onGoRegister) { Text("Registrate") }
            }
        }
    }
}