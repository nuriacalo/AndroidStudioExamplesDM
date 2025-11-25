package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.DummyJsonApiService

class DummyJsonRepository {
    suspend fun getUsers(): UserResponse

    class NetworkDummyJsonRepository(
        private val dummyJsonApiService: DummyJsonApiService
    ): DummyJsonRepository{
        override suspend fun getUsers(): UserResponse {
            return dummyJsonApiService.getUsers()
        }
    }}
}