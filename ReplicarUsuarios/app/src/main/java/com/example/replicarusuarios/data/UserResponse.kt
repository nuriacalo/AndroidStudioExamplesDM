package com.example.replicarusuarios.data

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val users: List<User>
)
