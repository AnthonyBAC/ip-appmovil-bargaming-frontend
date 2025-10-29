package com.example.bargaming_grupo4

// import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bargaming_grupo4.ui.app.MyApp
import com.example.bargaming_grupo4.ui.theme.BarGaming_Grupo4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarGaming_Grupo4Theme {
                // LLama MyApp - aqui inicia todp -
                MyApp()
            }
        }
    }
}