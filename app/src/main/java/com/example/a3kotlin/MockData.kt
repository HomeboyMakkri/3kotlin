package com.example.a3kotlin

object MockData {
    val productList = listOf(
        Product(
        id = "1",
        name = "Apple Iphone 15",
        price = 279900,
        description = "Пока нет описания",
        imageRes = R.drawable.iphone_15,
        features = listOf("Чёрный", "512ГБ", "eSim")
    ),
        Product(
            id = "2",
            name = "Apple Watch 15",
            price = 279900,
            description = "Пока нет описания",
            imageRes = R.drawable.apple_watch,
            features = listOf("Чёрный", "12", "Есть")
        ),Product(
            id = "3",
            name = "MacBook Pro",
            price = 1279900,
            description = "Пока нет описания",
            imageRes = R.drawable.macbook_pro,
            features = listOf("Чёрный", "1024ГБ")
        ),
        Product(
            id = "4",
            name = "Apple Iphone 15",
            price = 279900,
            description = "Пока нет описания",
            imageRes = R.drawable.iphone_15,
            features = listOf("Чёрный", "512ГБ", "eSim")
        ),

    )
    fun getMockedProducts(): List<Product> {
        return productList
    }
}