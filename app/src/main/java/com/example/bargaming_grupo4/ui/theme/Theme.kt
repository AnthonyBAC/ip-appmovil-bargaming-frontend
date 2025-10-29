package com.example.bargaming_grupo4.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PinkButton,
    onPrimary = WhiteVariant,
    secondary = BgSecondary,
    onSecondary = WhiteVariant,
    background = BgPrimary,
    onBackground = WhiteVariant,
    surface = BgGradient,
    onSurface = WhiteVariant,
    tertiary = NeonAccent
)

@Composable
fun BarGaming_Grupo4Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}