package com.example.a3kotlin

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale

@Composable
fun Content(modifier: Modifier = Modifier) {
    val products = MockData.getMockedProducts()
    Column(modifier = modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
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
fun ProductCard(product: Product) {
    val cardColor = colorResource(id = R.color.card_color)
    val textColor = colorResource(id = R.color.black)
    val buttonColor = colorResource(id = R.color.lil_button_or_add_pay_address)
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(cardColor)
        ,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                    containerColor = buttonColor // Задаем цвет фона кнопки
                )
            ) {
                Text(
                    text = "В корзину",
                    color = textColor, // Цвет текста на кнопке
                    style = TextStyle(fontSize = 18.sp),
                    maxLines = 1, // Убедимся, что текст не выходит за пределы кнопки
                    overflow = TextOverflow.Ellipsis, // Текст не будет выходить за пределы
                    textAlign = TextAlign.Center
                )
            }
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

    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        paddingValues ->
        Content(modifier = Modifier.fillMaxSize().padding(paddingValues))
    }
}

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController, startDestination = NavigationItems.Home.route){

        composable(NavigationItems.Home.route){
            MainScreen()
        }

        composable(NavigationItems.Catalog.route){
            MainScreen()
        }

        composable(NavigationItems.ShoppingCard.route){
            MainScreen()
        }
        composable(NavigationItems.Lovely.route){
            MainScreen()
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
        NavigationItems.Lovely
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
                                        .widthIn(min = 16.dp), // Минимальная ширина
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
                    // Всегда переходим на MainScreen
                    navController.navigate(NavigationItems.Home.route) {
                        popUpTo(NavigationItems.Home.route) { 
                            saveState = true 
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
