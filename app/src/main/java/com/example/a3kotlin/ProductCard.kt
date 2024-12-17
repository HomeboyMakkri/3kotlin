package com.example.a3kotlin

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ProductCard(
    product: Product,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    navController: NavController
) {

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
            .clickable {
                navController.navigate("productDetail/${product.id}")
            }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {


            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isPortrait) 150.dp else 220.dp)
                    .clip(RoundedCornerShape(8.dp)),
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

            Text(
                text = "${product.price} ₽",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp),
                color = textColor
            )

            Spacer(modifier = Modifier.height(8.dp))

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