package com.example.replicarusuarios.data

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val department: String,
    val name: String,
    val title: String,
    val address: Address
)

data class Address(
    val address: String,
    val city: String,
    val state: String,
    val stateCode: String,
    val postalCode: String,
    val coordinates: Coordinates,
    val country: String,
)

data class Coordinates(
    val lat: Double,
    val lng: Double
)

