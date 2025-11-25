package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.MockApiService

interface MockApiRepository {

    suspend fun getUsers(): List<User>

    suspend fun createUser(user: User): User

    suspend fun deleteUser(id: Int): User
}

class NetworkMockApiRepository(
    private val mockApiService: MockApiService
) : MockApiRepository {
    override suspend fun getUsers(): List<User> =
        mockApiService.getUsers()

    override suspend fun createUser(user: User): User =
        mockApiService.createUser(user)

    override suspend fun deleteUser(id: Int) =
        mockApiService.deleteUser(id)

}
