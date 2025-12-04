package com.example.gestionproductos.model
import com.example.gestionproductos.data.CartItem
import com.example.gestionproductos.data.Product

data class ProductsUiState (
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItem> = emptyList()
)