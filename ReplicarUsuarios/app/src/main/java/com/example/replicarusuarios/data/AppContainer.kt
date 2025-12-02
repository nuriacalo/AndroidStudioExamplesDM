package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.DummyJsonApiService
import com.example.replicarusuarios.network.MockApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val dummyJsonRepository: DummyJsonRepository
    val mockApiRepository: MockApiRepository
}


class DefaultAppContainer : AppContainer {
    private val baseUrlDummyJson = "https://dummyjson.com"

    private val baseUrlMockApi = "https://692602be26e7e41498f909ed.mockapi.io/api/wirtz/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val retrofitDummyJson: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrlDummyJson)
        .build()

    private val retrofitServiceDummyJson: DummyJsonApiService by lazy {
        retrofitDummyJson.create(DummyJsonApiService::class.java)
    }

    override val dummyJsonRepository: DummyJsonRepository by lazy {
        NetworkDummyJsonRepository(retrofitServiceDummyJson)
    }

    private val retrofitMockApi: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrlMockApi)
        .build()

    private val retrofitServiceMockApi: MockApiService by lazy {
        retrofitMockApi.create(MockApiService::class.java)
    }

    override val mockApiRepository: MockApiRepository by lazy {
        NetworkMockApiRepository(retrofitServiceMockApi)
    }

}