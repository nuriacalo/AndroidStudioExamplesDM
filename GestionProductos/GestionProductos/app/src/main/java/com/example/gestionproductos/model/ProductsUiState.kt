package com.example.gestionproductos.model
import com.example.gestionproductos.data.CartItem
import com.example.gestionproductos.data.Product

data class ProductsUiState (
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val queryState: QueryState = QueryState.LOADING,
    val message: String = ""
)

enum class QueryState {
    LOADING,
    SUCCESS,
    ERROR
}