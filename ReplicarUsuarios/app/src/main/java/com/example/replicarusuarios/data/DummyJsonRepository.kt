package com.example.replicarusuarios.data

import com.example.replicarusuarios.network.DummyJsonApiService

interface DummyJsonRepository {
    suspend fun getUsers(): UserResponse
}

class NetworkDummyJsonRepository(
    private val dummyJsonApiService: DummyJsonApiService
) : DummyJsonRepository {
    override suspend fun getUsers(): UserResponse =
        dummyJsonApiService.getUsers(
            limit = 60,
            skip = 0,
            select = "id,firstName,lastName,image,age,email,username,password,gender,phone,height,weight,company"
        )
}
