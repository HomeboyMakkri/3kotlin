package com.example.a3kotlin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object FavoritesViewModel: ViewModel() {
    private var _favItems = mutableStateOf<List<Product>>(listOf())

    fun addProduct(product: Product) {
        _favItems.value = _favItems.value + product
    }

    fun removeProduct(product: Product) {
        _favItems.value = _favItems.value.filter { it.id != product.id }
    }

    fun getProducts(): List<Product> = _favItems.value

    fun getTotalItems(): Int = _favItems.value.size

    fun clear() {
        _favItems.value = listOf()
    }
}