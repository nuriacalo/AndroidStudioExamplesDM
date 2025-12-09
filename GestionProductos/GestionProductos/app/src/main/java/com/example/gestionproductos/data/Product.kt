package com.example.gestionproductos.data

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val description: String = "",
    val category: String = "",
    val stock: Int = 0,
    val price: Double = 0.0,
    val sku: String = "",
    val availabilityStatus: String = "",
    @DrawableRes val image: Int = 0,
    val thumbnail: String = ""
) {
    val shortDescription: String
        get() = if (description.length > 80) {
            description.substring(0, 80) + "..."
        } else {
            description
        }
}
