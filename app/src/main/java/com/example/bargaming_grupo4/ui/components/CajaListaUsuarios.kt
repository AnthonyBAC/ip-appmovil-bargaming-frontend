package com.example.bargaming_grupo4.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bargaming_grupo4.model.LoginRequest

@Composable
fun CajaListaUsuarios(
    title: String,
    users: List<LoginRequest>,
    navController: NavController
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            users.forEach { user ->
                UserCard(
                    nombre = user.email
                )
            }
        }
    }
}