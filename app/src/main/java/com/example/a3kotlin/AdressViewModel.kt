package com.example.a3kotlin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object AdressViewModel: ViewModel() {
    private var _adressList = mutableStateOf<List<String>>(listOf())

    fun addAdress(adress: String){
        _adressList.value += adress
    }

    fun deleteAdress(adress:String){
        _adressList.value = _adressList.value.filter { it != adress }
    }

    fun isAdressExist(adress: String): Boolean{

        return _adressList.value.contains(adress)
    }

    fun getAdresses(): List<String> = _adressList.value
}