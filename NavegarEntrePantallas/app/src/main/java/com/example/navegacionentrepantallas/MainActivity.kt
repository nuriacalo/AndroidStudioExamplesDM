package com.example.navegacionentrepantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.navegacionentrepantallas.navegation.AppNavigation
import com.example.navegacionentrepantallas.ui.theme.NavegacionEntrePantallasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacionEntrePantallasTheme {
                AppNavigation()
            }
        }
    }
}
