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