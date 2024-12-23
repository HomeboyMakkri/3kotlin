package com.example.a3kotlin

sealed class NavigationItemsSec(var route: String) {
    data object Address : NavigationItemsSec("address")
    data object Payment : NavigationItemsSec("payment")
    data object Success : NavigationItemsSec("success")
}