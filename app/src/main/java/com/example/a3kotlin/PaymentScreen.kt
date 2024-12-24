package com.example.a3kotlin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PaymentScreen(navController: NavHostController, favoritesViewModel: FavoritesViewModel = viewModel()) {

    val paymentMethods = PaymentViewModel.getMethods()
    val selectedOption = remember { mutableStateOf<String?>(null) }
    val isDropdownVisible = remember { mutableStateOf(false) }

    val isAddingCard = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Выберите способ оплаты",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )


        PaymentOption(
            label = "Оплатить картой при получении",
            isSelected = selectedOption.value == "card_on_delivery",
            onSelect = { selectedOption.value = "card_on_delivery" }
        )
        PaymentOption(
            label = "Оплатить наличными курьеру",
            isSelected = selectedOption.value == "cash",
            onSelect = { selectedOption.value = "cash" }
        )
        PaymentOption(
            label = "Оплатить картой онлайн",
            isSelected = selectedOption.value == "online",
            onSelect = {
                selectedOption.value = "online"
                isDropdownVisible.value = true
            }
        )

        if (selectedOption.value == "online" && isDropdownVisible.value) {
            Spacer(modifier = Modifier.height(16.dp))

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        "Сохраненные карты:",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(10.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (paymentMethods.isNotEmpty()) {
                                itemsIndexed(paymentMethods) { index, card ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = card, modifier = Modifier.weight(1f))

                                        IconButton(
                                            onClick = {
                                                PaymentViewModel.removeMethod(card)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Удалить карту"
                                            )
                                        }
                                    }
                                }
                            }
                            else {

                                item {
                                    Box (contentAlignment = Alignment.Center){
                                        Text(text = "Сохранненые карты отсутствуют, добавьте новый способ оплаты",
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold),
                                            color = Color.Gray)
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                                item {
                                Button(
                                    onClick = {
                                        isAddingCard.value = true
                                        PaymentViewModel.addMethod(
                                            "***${kotlin.random.Random.nextInt(1000, 10000)}"
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.add_button))
                                ) {
                                    Text(text = "Добавить новую карту")
                                }
                            }
                            item {
                                Button(
                                    onClick = {
                                        CartViewModel.clear()
                                        navController.navigate(NavigationItemsSec.Success.route) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.add_button))
                                ) {
                                    Text(text = "Оплатить!")
                                }
                            }
                        }
                    }

                    }


                }
        }


@Composable
fun PaymentOption(label: String, isSelected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onSelect() },
            colors = RadioButtonDefaults.colors(
                selectedColor = colorResource(id = R.color.final_buttons)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = TextStyle(fontSize = 16.sp))
    }
}
