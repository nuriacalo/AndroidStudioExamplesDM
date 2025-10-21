package com.example.navigationcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationcompose.data.Product

@Composable
fun ProductListComponent(product: Product, onClick:(Int)-> Unit) {
    Card(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.elevatedShape
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = { onClick(product.id) })
                .padding(8.dp)
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(product.image),
                contentDescription = product.title,
                modifier = Modifier.padding(end = 20.dp)
            )
            Text(
                text = product.title,
                fontSize = 16.sp
            )
        }
    }
}
