package com.example.navegacionentrepantallas.navegation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar({ Text(text = "First Screen") })
        }) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hola Navegación")
            Button(onClick = {navController.navigate(route = AppScreens.SecondScreen.route+ "/Este es un parametro")}) {
                Text("Navega")
            }
        }
    }
}

