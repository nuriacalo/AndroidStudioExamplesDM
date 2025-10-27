package com.example.navigationcompose.ui.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationcompose.data.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenAdvanced(product: Product, onBack: () -> Unit) {
    val mContext = LocalContext.current
    val scrollState = rememberScrollState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column() {
                        Text("Detalle del producto")
                        Text("SKU: ${product.sku}", fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack()}) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                })
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Image(
                    painter = painterResource(product.image),
                    contentDescription = product.description
                )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.category.uppercase(),
                fontSize = 16.sp,
                modifier = Modifier.padding(top=4.dp)
            )

            HorizontalDivider(modifier = Modifier.height(16.dp))

            Text(
                text = product.description,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 4.dp, end = 4.dp)
            )

            HorizontalDivider(modifier = Modifier.padding(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                val stockDisponible = if (product.stock > 0) true else false
                val textDisponible = if (stockDisponible) "En stock" else "Agotado"
                val colorDisponible = if (stockDisponible) Color(0xFF4CAF50) else Color.Red
                Text(text = "${product.price} €",
                    fontSize = 20.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = "$textDisponible",
                    fontSize = 20.sp,
                    color = colorDisponible
                    )

                if (stockDisponible) {
                    Text(
                        text = "${product.stock} uds.",
                        fontSize = 20.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                Button(onClick = {  }) {
                    Text(text = "Añadir al Carrito")
                }
            }

        }
    }
}
