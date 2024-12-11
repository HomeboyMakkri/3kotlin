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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController, startDestination = NavigationItems.Home.route){

        composable(NavigationItems.Home.route){
            MainScreen()
        }

        composable(NavigationItems.Catalog.route){
            CatalogScreen()
        }

        composable(NavigationItems.ShoppingCard.route){
            ShoppingCardScreen()
        }
        composable(NavigationItems.Favorites.route){
            FavoritesScreen()
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val cartItemCount = CartViewModel.getTotalItems()
    val favItemCount = FavoritesViewModel.getTotalItems()

    val items = listOf(
        NavigationItems.Home,
        NavigationItems.Catalog,
        NavigationItems.ShoppingCard,
        NavigationItems.Favorites
    )

    val itemColor = colorResource(id = R.color.white)
    val backgroundColor = colorResource(id = R.color.top_down_color)
    val accentColor = colorResource(id = R.color.lil_button_or_add_pay_address)

    BottomNavigation(
        backgroundColor = backgroundColor,
        contentColor = itemColor,
        modifier = Modifier
            .height(64.dp)
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    if (item.route == NavigationItems.Favorites.route && favItemCount != 0) {
                        BadgedBox(
                            modifier = Modifier.wrapContentSize(),
                            badge = {
                                Surface(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .widthIn(min = 16.dp),
                                    shape = CircleShape,
                                    color = accentColor,
                                    tonalElevation = 4.dp
                                ) {
                                    Text(
                                        text = if (favItemCount > 99) "99+" else favItemCount.toString(),
                                        color = backgroundColor,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp),
                                tint = if (currentRoute == item.route) accentColor else itemColor
                            )
                        }
                    }
                    if (item.route == NavigationItems.ShoppingCard.route && cartItemCount != 0) {
                        BadgedBox(
                            modifier = Modifier.wrapContentSize(),
                            badge = {
                                Surface(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .widthIn(min = 16.dp),
                                    shape = CircleShape,
                                    color = accentColor,
                                    tonalElevation = 4.dp
                                ) {
                                    Text(
                                        text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                                        color = backgroundColor,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp),
                                tint = if (currentRoute == item.route) accentColor else itemColor
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp),
                            tint = if (currentRoute == item.route) accentColor else itemColor
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        color = itemColor
                    )
                },
                selectedContentColor = accentColor,
                unselectedContentColor = itemColor,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route = route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}