package com.example.a3kotlin

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    val imageRes: Int,
    val features: List<String>) {
//    fun getId(): Int {
//        return id
//    }
}
