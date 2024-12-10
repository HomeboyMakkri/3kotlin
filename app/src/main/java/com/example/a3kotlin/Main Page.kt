package com.example.a3kotlin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


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
                painter = painterResource(id = product.imageRes), // Используем ресурс изображения
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
                onClick = {},
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
    ) { paddingValues ->
        Content(modifier = Modifier.fillMaxSize().padding(paddingValues))
    }
}

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController, startDestination = NavigationItems.Home.route){

        composable(NavigationItems.Home.route){
            TODO("HomeScreen()")
        }

        composable(NavigationItems.Catalog.route){
            TODO("CatalogScreen()")
        }

        composable(NavigationItems.ShoppingCard.route){
            TODO("ShoppingCardScreen()")
        }
        composable(NavigationItems.Lovely.route){
            TODO("LovelyScreen()")
        }

    }

}

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        NavigationItems.Home,
        NavigationItems.Catalog,
        NavigationItems.ShoppingCard,
        NavigationItems.Lovely
    )


    val itemColor = colorResource(id = R.color.white)

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.top_down_color),
        contentColor = itemColor,
        modifier = Modifier.height(72.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        tint = itemColor
                    )
                },
                selectedContentColor = itemColor,
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

