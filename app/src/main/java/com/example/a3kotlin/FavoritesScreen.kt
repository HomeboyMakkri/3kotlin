package com.example.a3kotlin

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
fun FavoritesScreen(navController: NavHostController) {

    val products = FavoritesViewModel.getProducts()
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
                text = "Избранное",
                style = TextStyle(fontSize = 20.sp,fontWeight = FontWeight.Bold)
            )
        }
        if (products.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column (verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Text(
                            text = "У вас пока нет избранных товаров",
                            style = TextStyle(fontSize = 25.sp, color = Color.Gray),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(40.dp))
                        Button(
                            onClick = { navController.navigate(NavigationItems.Home.route)},
                            colors = ButtonDefaults.buttonColors(
                                buttonColor
                            )
                            )


                         {
                            Text(
                                text = "Вернуться на главную",
                                color = Color.Black
                            )
                        }
                    }
                }
            }


    else {



            Box(
                modifier = Modifier.fillMaxWidth()
                  ,
                contentAlignment = Alignment.CenterEnd
            )
            {
                Button(
                    onClick = {FavoritesViewModel.clear()},
                    modifier = Modifier.padding(10.dp)
                        .wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(
                        buttonColor
                    )
                    ) {
                    Text(
                        text = "Очистить избранное",
                        style = TextStyle(fontSize = 15.sp),
                        color = colorResource(id = R.color.black)
                    )
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns), // Устанавливаем количество столбцов в зависимости от ориентации
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductCard(product = product, navController = navController)
                }
            }
        }
    }
}
