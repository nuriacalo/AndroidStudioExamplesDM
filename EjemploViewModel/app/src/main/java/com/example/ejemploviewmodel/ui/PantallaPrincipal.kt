package com.example.ejemploviewmodel.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ejemploviewmodel.ui.componentes.ComponenteA
import com.example.ejemploviewmodel.ui.componentes.ComponenteB
import com.example.ejemploviewmodel.ui.componentes.ComponenteC
import com.example.ejemploviewmodel.viewmodel.EjemploAppViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipal (modifier: Modifier = Modifier, viewModel: EjemploAppViewModel =viewModel()) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
        ComponenteA(modifier.height(80.dp))
        ComponenteB(modifier.height(80.dp))
        ComponenteC(modifier.height(80.dp))
    }
    val uiState by viewModel.uiState.collectAsState()

}
