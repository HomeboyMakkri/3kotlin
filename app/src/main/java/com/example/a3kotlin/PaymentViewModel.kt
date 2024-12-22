package com.example.a3kotlin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object PaymentViewModel: ViewModel() {
    private var _paymentMethods = mutableStateOf<List<String>>(listOf())

    fun addMethod (name: String){
        _paymentMethods.value = _paymentMethods.value + name
    }

    fun removeMethod (name: String){
        _paymentMethods.value = _paymentMethods.value.filter {it != name}
    }

    fun getMethods() : List<String> = _paymentMethods.value

}