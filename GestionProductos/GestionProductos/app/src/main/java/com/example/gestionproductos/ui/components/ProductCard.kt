package com.example.navigationcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestionproductos.R
import com.example.gestionproductos.data.Product

@Composable
fun ProductCard(product: Product,
                modifier: Modifier = Modifier,
                onClick: (Int) ->Unit) {
    Card (
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .clickable( onClick = { onClick(product.id) } ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = CardDefaults.elevatedShape
    ){
        Row (horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(product.image),
                contentDescription = product.title,
                modifier = modifier
                    .size(width = 80.dp, height = 80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )


            Column (Modifier.weight(2f)) {
                Text(
                    text = product.title,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(all = 8.dp)
                )
                Text(
                    text = product.description,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(all = 8.dp)
                )
            }


            Column (Modifier.weight(1f).padding(16.dp),
            ) {

                Column(
                    Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "STOCK",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = "${product.stock} uds.",
                        color = Color.Blue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                HorizontalDivider(Modifier.height(4.dp))

                Column(
                    Modifier.padding(4.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "PRECIO",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Text(
                        text = "${product.price} €",
                        color = Color.Blue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductCard() {
    val product = Product(
        id = 34,
        title = "Café Nescafé",
        description = "Café de calidad Nescafé, disponible en varias mezclas para una taza rica y satisfactoria.",
        category = "alimentación",
        stock = 57,
        price = 7.99,
        sku = "GRO-BRD-NES-034",
        availabilityStatus = "En stock",
        image = R.drawable.nescafe,
        thumbnail = "https://cdn.dummyjson.com/product-images/groceries/nescafe-coffee/thumbnail.webp"
    )
    ProductCard(product, onClick = {})
}