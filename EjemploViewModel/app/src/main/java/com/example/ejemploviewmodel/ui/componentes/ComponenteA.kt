package com.example.ejemploviewmodel.ui.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun ComponenteA(estadoSwitch: Boolean, onIncrementar: () -> Unit, onSwitchChange: () -> Unit, modifier: Modifier = Modifier){
    Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = modifier.fillMaxWidth() ) {
        Button(onClick = {} ) {
            Text(text = "Suma +1", fontSize =  24.sp)
        }
        Switch(checked = false, onCheckedChange = {})
    }
}