package com.example.gestionproductos.data

import com.example.gestionproductos.network.ProductApiService

interface ProductRepository {
    suspend fun getProducts(): ProductResponse
}

class NetworkProductRepository(
    private val productApiService: ProductApiService
) : ProductRepository {
    override suspend fun getProducts(): ProductResponse {
        return productApiService.getProducts(
            limit = 200,
            skip = 0,
            select = "id,title,description,category,stock,price,sku,availabilityStatus,thumbnail"
        )
    }
}