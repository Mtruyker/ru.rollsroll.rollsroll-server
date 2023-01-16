package ru.rollsroll.database.product

data class ProductDTO(
    val id: String,
    val title: String,
    val logo: String,
    val description: String,
    val weight: String,
    val price: String,
)