package com.example.bargaming_grupo4.ui.app

// import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.bargaming_grupo4.ui.utils.obtenerWindowSizeClass


// Desicion cual tipo de pantalla ocupara
@Composable
fun MyApp() {
    val windowSize = obtenerWindowSizeClass()
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> CompactApp()
        // ----------------------
        // Estas no se ocuparan aun
        //WindowWidthSizeClass.Medium -> MediumApp()
        // WindowWidthSizeClass.Expanded -> ExpandedApp()
        else -> CompactApp()
    }
}