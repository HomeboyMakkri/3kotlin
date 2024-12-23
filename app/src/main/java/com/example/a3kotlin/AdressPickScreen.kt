package com.example.a3kotlin


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun AdressPickScreen(navController: NavHostController, adressViewModel: AdressViewModel = viewModel()){
    val adresses = AdressViewModel.getAdresses()
    val selectedAdress = remember { mutableStateOf<String?>(null) }
    val buttonColor = colorResource(id = R.color.lil_button_or_add_pay_address)

    Scaffold(
        topBar = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Адрес доставки",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                // Первая кнопка
                Button(
                    onClick = { adressViewModel.addAdress("Адрес ${adresses.size + 1}") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text(text = "Добавить адрес", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(10.dp))  // Отступ между кнопками

                // Вторая кнопка
                Button(
                    onClick = {
                        navController.navigate(NavigationItemsSec.Payment.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(buttonColor)
                ) {
                    Text(text = "Перейти к оплате", color = Color.Black)
                }
            }
        }
    ){ innerValues ->
        if(adresses.isEmpty()){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center ){
                Text(text = "Адреса доставки пока отсутвуют, добавьте новый адрес, чтобы продолжить заказ",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = Color.Gray,
                    textAlign = TextAlign.Center)
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerValues)
    ) {
            items(adresses, key = {it}) {adress ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                AdressItem(
                    adress = adress,
                    isSelected = adress == selectedAdress.value,
                    onClick = {selectedAdress.value = adress},
                    onDelete = {adressViewModel.deleteAdress(adress)}
                )
            }
        }
        }
    }

}

@Composable
fun AdressItem(adress:String,
               isSelected: Boolean,
               onClick: ()-> Unit,
               onDelete:() -> Unit){
    val interactionSource = remember { MutableInteractionSource() }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick,
                interactionSource = interactionSource, indication = null),
        verticalAlignment = Alignment.CenterVertically
    ) { RadioButton(colors = RadioButtonDefaults.colors(
        selectedColor = colorResource(id = R.color.final_buttons)
    ),
        selected = isSelected,
        onClick = onClick
    )
        Column (
            modifier = Modifier.weight(1f)
                .padding(start = 6.dp)
        ){
            Text(text = adress,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold) ,
                color = Color.Black)
            Text(text = "Дополнительная информация об адресе ${AdressViewModel.getAdresses().indexOf(adress) + 1}",
                style = TextStyle(fontSize = 14.sp, color = Color.Gray))
        }
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "Удалить")
        }
    }
}