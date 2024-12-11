package com.example.a3kotlin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object CartViewModel : ViewModel() {
    private var _cartItems = mutableStateOf<List<Product>>(listOf())

    fun addProduct(product: Product) {
        _cartItems.value = _cartItems.value + product
    }

    fun removeProduct(product: Product) {
        _cartItems.value = _cartItems.value.filter { it.id != product.id }
    }

    fun getProducts(): List<Product> = _cartItems.value

    fun getTotalItems(): Int = _cartItems.value.size

    fun clear() {
        _cartItems.value = listOf()
    }
}