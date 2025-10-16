package com.example.bargaming_grupo4.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode

val GradientMain = Brush.linearGradient(
    colors = listOf(
        BgPrimary,
        BgPrimary,
        BgGradient
    ),
    tileMode = TileMode.Clamp
)
