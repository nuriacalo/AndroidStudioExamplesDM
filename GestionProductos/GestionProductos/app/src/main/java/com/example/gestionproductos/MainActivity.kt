package com.example.gestionproductos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.gestionproductos.ui.theme.GestionProductosTheme
import com.example.gestionproductos.ui.views.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionProductosTheme {
                AppNavigation()
            }
        }
    }
}
