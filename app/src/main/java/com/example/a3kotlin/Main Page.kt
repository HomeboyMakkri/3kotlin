package com.example.a3kotlin

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ProductCard(product: Product, favoritesViewModel: FavoritesViewModel = viewModel()) {
    val cardColor = colorResource(id = R.color.card_color)
    val textColor = colorResource(id = R.color.black)
    val buttonColor = colorResource(id = R.color.lil_button_or_add_pay_address)

    val favoriteProducts = favoritesViewModel.getProducts()
    val isFavorite = favoriteProducts.any { it.id == product.id }


    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(cardColor)
    ) {
        // Контент карточки
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Изображение продукта
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isPortrait) 150.dp else 100.dp) // Адаптируем высоту в зависимости от ориентации
                    .clip(RoundedCornerShape(8.dp)), // Округляем края изображения
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Название продукта
            Text(
                color = textColor,
                text = product.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )

            // Цена товара
            Text(
                text = "${product.price} ₽",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp),
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Кнопка "В корзину"
            Button(
                onClick = { CartViewModel.addProduct(product) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text(
                    text = "В корзину",
                    color = textColor,
                    style = TextStyle(fontSize = 18.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Кнопка "Избранное" в правом верхнем углу
        IconButton(
            onClick = {
                if (isFavorite) {
                    favoritesViewModel.removeProduct(product)
                } else {
                    favoritesViewModel.addProduct(product)
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(if (isPortrait) 30.dp else 40.dp) // Увеличиваем размер кнопки в горизонтальной ориентации
                .clip(CircleShape) // Округляем кнопку
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Add to favorites",
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val bgColor = colorResource(id = R.color.top_down_color)
    val mainColor = colorResource(id = R.color.white)

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo), // Логотип
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Город",
                        color = Color.White,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = bgColor,
            titleContentColor = mainColor
        )
    )
}

@Composable
fun MainScreen() {
    Content(modifier = Modifier)
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    val products = MockData.getMockedProducts()
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

    Column(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(products) { product ->
                ProductCard(product = product)
            }
        }
    }
}

@Composable
fun MainPage() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Navigation(navController)
        }
    }
}

@Composable
fun CatalogScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "CATALOG SCREEN"
        )
    }
}

@Composable
fun FavoritesScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "FAVORITES"
        )
    }
}

@Composable
fun ShoppingCardScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Shopping"
        )
    }
}

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
