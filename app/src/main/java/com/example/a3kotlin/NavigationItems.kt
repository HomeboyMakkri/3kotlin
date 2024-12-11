package com.example.a3kotlin

sealed class NavigationItems(var route: String, var icon: Int, var title: String)
{
    data object Home : NavigationItems("home", R.drawable.home, "Главная")
    data object Catalog : NavigationItems("catalog", R.drawable.menu, "Каталог")
    data object ShoppingCard : NavigationItems("shopping_cart", R.drawable.shopping_cart_outlined, "Корзина")
    data object Favorites : NavigationItems("favorites", R.drawable.favorite_outlined, "Избранное")
}