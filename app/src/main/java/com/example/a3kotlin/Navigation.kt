package com.example.a3kotlin

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController, startDestination = NavigationItems.Home.route) {

        composable(NavigationItems.Home.route) {
            HomeScreen(navController)
        }

        composable(NavigationItems.Catalog.route) {
            CatalogScreen(navController)
        }

        composable(NavigationItems.ShoppingCard.route) {
            ShoppingCardScreen(navController)
        }
        composable(NavigationItems.Favorites.route) {
            FavoritesScreen(navController)
        }
        composable(
            "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = MockData.productList.firstOrNull { it.id == productId }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    navController = navController
                )
            } else {
                Text("Product not found")
            }
        }
        composable(NavigationItemsSec.Address.route){
            AdressPickScreen(navController)
        }
        composable(NavigationItemsSec.Payment.route){
            PaymentScreen(navController)
        }
        composable(NavigationItemsSec.Success.route){
            Success(navController)
        }
    }
}