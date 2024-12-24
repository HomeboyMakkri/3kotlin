package com.example.a3kotlin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val isLoadedKey = "is_loaded"
    private var isLoaded = savedStateHandle.get<Boolean>(isLoadedKey) ?: false

    private val mockProducts = MockData.getMockedProducts()

    fun loadProducts() {
        // Проверяем, нужно ли загружать данные
        if (!isLoaded && !_isLoading.value) {
            _isLoading.value = true
            viewModelScope.launch {
                delay(2000) // Симулируем задержку
                _products.value = mockProducts
                isLoaded = true
                savedStateHandle[isLoadedKey] = isLoaded
                _isLoading.value = false
            }
        }
    }
}