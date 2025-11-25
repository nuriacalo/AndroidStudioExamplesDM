package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.MockApiService

class  MockApiRepository {

    class NetworkMockApiRepository(
         private val mockApiService: MockApiService
    ) : MockApiRepository {
         override suspend fun getUsers(): List<User>  =
            return mockApiService.getUsers()

    }
}