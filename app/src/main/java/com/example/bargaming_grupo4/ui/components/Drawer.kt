package com.example.bargaming_grupo4.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import com.example.bargaming_grupo4.ui.theme.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class DrawerItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun AppDrawer(
    currentRoute: String?,
    items: List<DrawerItem>,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerContainerColor = BgPrimary,
        drawerContentColor = WhiteVariant
    ) {
        Text(
            text = "BarGaming",
            style = MaterialTheme.typography.headlineSmall.copy(
                color = WhiteVariant,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(16.dp)
        )

        items.forEach { item ->
            NavigationDrawerItem(
                label = {
                    Text(
                        item.label,
                        color = WhiteVariant
                    )
                },
                selected = false,
                onClick = item.onClick,
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = WhiteVariant
                    )
                },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = PinkButton,
                    unselectedContainerColor = BgPrimary,
                    unselectedIconColor = NeonAccent,
                    selectedTextColor = WhiteHover,
                    unselectedTextColor = WhiteVariant
                )
            )
        }
    }
}

@Composable
fun defaultDrawerItems(
    onHome: () -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit
): List<DrawerItem> = listOf(
    DrawerItem("Home", Icons.Filled.Home, onHome),
    DrawerItem("Login", Icons.Filled.AccountCircle, onLogin),
    DrawerItem("Registro", Icons.Filled.Person, onRegister)
)