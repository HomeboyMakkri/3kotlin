package com.example.a3kotlin

sealed class NavigationItems(var route: String, var icon: Int, var title: String)
{
    data object Home : NavigationItems("home", R.drawable.home, "Home")
    data object Catalog : NavigationItems("catalog", R.drawable.menu, "Catalog")
    data object ShoppingCard : NavigationItems("shopping_cart", R.drawable.shopping_cart, "Shopping Card")
    data object Lovely : NavigationItems("heart", R.drawable.heart, "Heart")
}