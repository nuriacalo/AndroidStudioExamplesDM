package com.example.gestionproductos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gestionproductos.GestionProductosApplication
import com.example.gestionproductos.data.CartItem
import com.example.gestionproductos.data.Product
import com.example.gestionproductos.data.ProductRepository
import com.example.gestionproductos.data.ProductResponse
import com.example.gestionproductos.model.ProductsUiState
import com.example.gestionproductos.model.QueryState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        getProducts()
    }

    fun getProductById(id: Int): Product {
        return _uiState.value.products.firstOrNull { it.id == id }
            ?: Product(id = id, title = "Producto no encontrado")
    }

    fun addToCart(product: Product, quantity: Int) {
        val nuevaListaCartItems =
            if (_uiState.value.cartItems.any { it.id == product.id }) {
                _uiState.value.cartItems.map {
                    if (it.id == product.id) {
                        it.copy(quantity = it.quantity + quantity)
                    } else {
                        it
                    }
                }
            } else {
                val nuevoCartItem = CartItem(
                    id = product.id,
                    title = product.title,
                    image = product.image,
                    price = product.price,
                    quantity = quantity
                )
                _uiState.value.cartItems + nuevoCartItem
            }
        _uiState.value = _uiState.value.copy(cartItems = nuevaListaCartItems)
    }

    fun removeFromCart(id: Int) {
        val updatedCart = _uiState.value.cartItems.filter { it.id != id }
        _uiState.value = _uiState.value.copy(cartItems = updatedCart)
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(
                queryState = QueryState.LOADING,
                message = "Cargando productos..."
            )
            try {
                val response: ProductResponse = productRepository.getProducts()
                _uiState.value = _uiState.value.copy(
                    products = response.products,
                    queryState = QueryState.SUCCESS,
                    message = "${response.total} Productos cargados"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    queryState = QueryState.ERROR,
                    message = "Error cargando productos: ${e.message}"
                )
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GestionProductosApplication)
                val products = application.container.productRepository
                ProductViewModel(productRepository = products)
            }
        }
    }
}