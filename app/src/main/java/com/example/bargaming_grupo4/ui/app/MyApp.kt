package com.example.bargaming_grupo4.ui.app

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable

@Composable
fun MyApp(windowSizeClass: WindowSizeClass){
    when (windowSizeClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> CompactApp()

        // ----------------------
        // Estas no se ocuparan aun
        //WindowWidthSizeClass.Medium -> MediumApp()
        // WindowWidthSizeClass.Expanded -> ExpandedApp()
        else -> CompactApp()
    }
}