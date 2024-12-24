package com.example.a3kotlin

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment


@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(products.isEmpty() && !isLoading) {
        viewModel.loadProducts()
    }

    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductCard(product = product, navController = navController)
            }
        }
    }
}

