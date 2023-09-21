package com.example.albertsonstask.data.repository.datasource

import com.example.albertsonstask.data.model.ProductsItem

interface ProductLocalDataSource {
    suspend fun getSavedProducts(category: String? = null): List<ProductsItem>
    suspend fun getSavedProduct(id: Int): ProductsItem
    suspend fun save(product: ProductsItem): Long
    suspend fun remove(product: ProductsItem): Int
}