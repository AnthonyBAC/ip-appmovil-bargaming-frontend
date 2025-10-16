package com.example.bargaming_grupo4.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PinkButton,          // Botones principales
    onPrimary = WhiteVariant,      // Texto dentro de botones
    secondary = BgSecondary,       // Contenedores secundarios
    onSecondary = WhiteVariant,    // Texto sobre fondo secundario
    background = BgPrimary,        // Fondo general (nav, footer)
    onBackground = WhiteVariant,   // Texto sobre fondo principal
    surface = BgGradient,          // Fondos de secciones o cards
    onSurface = WhiteVariant,      // Texto sobre surface
    tertiary = NeonAccent          // Color de acento / hover
)

@Composable
fun BarGaming_Grupo4Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}