package com.example.gestionproductos.data

import com.example.gestionproductos.network.ProductApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val productRepository: ProductRepository
}

class DefaultAppContainer : AppContainer {

   // https://dummyjson.com/products?limit=30&skip=30&select=id,title,description,category,stock,price,sku,availabilityStatus,images,thumbnail

    private val baseUrlDummyJson = "https://dummyjson.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
        explicitNulls = false
    }

    private val retrofitDummyJson = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrlDummyJson)
        .build()

    private val retrofitServiceDummyJson: ProductApiService by lazy {
        retrofitDummyJson.create(ProductApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy {
        NetworkProductRepository(retrofitServiceDummyJson)
    }
}