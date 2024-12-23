package com.example.a3kotlin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
                // Передаем в экран ProductDetailScreen не только product, но и navController и cartViewModel
                ProductDetailScreen(
                    product = product,
                    navController = navController
                )
            } else {
                Text("Product not found")
            }
        }
    }
}