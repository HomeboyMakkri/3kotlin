package com.example.a3kotlin

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ShoppingCardScreen(navController: NavHostController) {
    val products = CartViewModel.getProducts()
    val totalItems = CartViewModel.getTotalItems()
    val totalPrice = products.sumOf { it.price }
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
    val buttonColor = colorResource(id = R.color.lil_button_or_add_pay_address)

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Корзина",
                style = TextStyle(fontSize = 20.sp,fontWeight = FontWeight.Bold)
            )
        }

        if (products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column (verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ваша корзина пуста",
                        style = TextStyle(fontSize = 25.sp, color = Color.Gray),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = { navController.navigate(NavigationItems.Home.route)},
                        colors = ButtonDefaults.buttonColors(
                            buttonColor
                        )
                    ) {
                        Text (
                            text = "Вернуться на главную",
                            color = Color.Black
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            )
            {
                Button(
                    onClick = {CartViewModel.clear()},
                    modifier = Modifier.padding(10.dp)
                        .wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        buttonColor
                    )
                ) {
                    Text(
                        text = "Очистить корзину",
                        style = TextStyle(fontSize = 15.sp),
                        color = colorResource(id = R.color.black)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(products) { product ->
                    CartCard(product = product, navController = navController)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "Итого: $totalPrice ₽",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            navController.navigate(NavigationItemsSec.Address.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.add_button))
                    ) { Text(
                        text = "Перейти к оплате"
                    ) }
                }
            }
        }
    }
}