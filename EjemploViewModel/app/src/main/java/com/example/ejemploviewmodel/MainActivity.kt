package com.example.ejemploviewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ejemploviewmodel.ui.PantallaPrincipal
import com.example.ejemploviewmodel.ui.theme.EjemploViewModelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjemploViewModelTheme {
                PantallaPrincipal()
            }
        }
    }
}


