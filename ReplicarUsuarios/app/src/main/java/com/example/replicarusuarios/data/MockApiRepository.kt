package com.example.replicarusuarios.data

interface  MockApiRepository {

    class NetworkMockApiRepository(
         private val mockApiService: MockApiService
    ) : MockApiRepository {
         override suspend fun getUsers(): List<User>  =
            return mockApiService.getUsers()

    }
}