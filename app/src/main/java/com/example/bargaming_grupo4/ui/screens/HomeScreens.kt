package com.example.bargaming_grupo4.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.bargaming_grupo4.ui.utils.obtenerWindowSizeClass

@Composable
fun HomeScreens() {
    val windowSizeClass = obtenerWindowSizeClass()
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompact()
    }
}