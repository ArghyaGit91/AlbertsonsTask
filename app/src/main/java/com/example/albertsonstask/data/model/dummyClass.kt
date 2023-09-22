package com.example.albertsonstask.data.model
val images = listOf<String>("https://i.dummyjson.com/data/products/7/1.jpg")
val dummyProducts=  ProductsItem(4.25,"https://i.dummyjson.com/data/products/7/thumbnail.jpg",
    images,1499,4.15,"Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched",
    7,"Samsung Galaxy Book",50,"laptops","Samsung")
val searchResult =  SearchProductResponseModel(3, 3, 0, products = listOf(dummyProducts))

