package com.example.a3kotlin

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String,
    val imageRes: Int,
    val features: List<String>) {

}
