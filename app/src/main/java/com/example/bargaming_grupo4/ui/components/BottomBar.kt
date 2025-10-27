package com.example.bargaming_grupo4.ui.components

import com.example.bargaming_grupo4.ui.theme.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomBar(
    isLoggedIn: Boolean,
    onHome: () -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onAccount: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    BottomAppBar(
        containerColor = BgPrimary,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BarGamingNavButton(
                icon = Icons.Filled.Home,
                contentDescription = "Home",
                isSelected = selectedIndex == 0,
                onClick = {
                    selectedIndex = 0
                    onHome()
                }
            )

            BarGamingNavButton(
                icon = Icons.Filled.Favorite,
                contentDescription = "Favoritos",
                isSelected = selectedIndex == 1,
                onClick = {
                    selectedIndex = 1
                    onLogin()
                }
            )

            BarGamingNavButton(
                icon = Icons.Filled.ShoppingCart,
                contentDescription = "Carrito",
                isSelected = selectedIndex == 2,
                onClick = {
                    selectedIndex = 2
                    onRegister()
                }
            )

            //  Aquí controlamos si el usuario está logueado
            BarGamingNavButton(
                icon = Icons.Filled.AccountCircle,
                contentDescription = if (isLoggedIn) "Cerrar sesión" else "Cuenta",
                isSelected = selectedIndex == 3,
                onClick = {
                    selectedIndex = 3
                    if (isLoggedIn) {
                        onLogout()
                    } else {
                        onAccount()
                    }
                }
            )
        }
    }
}
