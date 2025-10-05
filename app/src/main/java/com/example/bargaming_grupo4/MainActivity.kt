package com.example.bargaming_grupo4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.bargaming_grupo4.ui.app.CompactApp

import com.example.bargaming_grupo4.ui.theme.BarGaming_Grupo4Theme
import com.example.bargaming_grupo4.ui.app.MyApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarGaming_Grupo4Theme {
                val windowSizeClass = calculateWindowSizeClass(this)
                CompactApp()
            }
        }
    }
}