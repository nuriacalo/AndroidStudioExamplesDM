package com.example.replicarusuarios.data


import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val department: String,
    val name: String,
    val title: String,
)



