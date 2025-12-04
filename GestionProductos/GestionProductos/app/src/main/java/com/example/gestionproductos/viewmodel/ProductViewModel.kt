package com.example.gestionproductos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gestionproductos.data.CartItem
import com.example.gestionproductos.data.DataSource
import com.example.gestionproductos.data.Product
import com.example.gestionproductos.model.ProductsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = ProductsUiState(products = DataSource.products)
    }

    fun getProductById(id: Int): Product {
        return _uiState.value.products.firstOrNull { it.id == id }
            ?: Product(id = id, title = "Producto no encontrado")
    }

    fun addToCart(product: Product, quantity: Int) {
        val nuevaListaCartItems =
            if ( _uiState.value.cartItems.any { it.id == product.id} ) {
                _uiState.value.cartItems.map{
                        if ( it.id == product.id) {
                            it.copy(quantity = it.quantity + quantity)
                        } else {
                            it
                        }
                }
            } else {
                val nuevoCartItem = CartItem(id = product.id,
                                        title =  product.title,
                                        image = product.image,
                                        price = product.price,
                                        quantity = quantity)
                _uiState.value.cartItems + nuevoCartItem
            }
        _uiState.value = _uiState.value.copy(cartItems =  nuevaListaCartItems)
    }

    fun removeFromCart(id: Int) {
        val updatedCart = _uiState.value.cartItems.filter { it.id != id }
        _uiState.value = _uiState.value.copy(cartItems = updatedCart)
    }

}