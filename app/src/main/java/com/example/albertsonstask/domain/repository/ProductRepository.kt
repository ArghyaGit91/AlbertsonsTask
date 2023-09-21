package com.example.albertsonstask.domain.repository

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.utils.Resource

interface ProductRepository {

    /**
     * Remote
     */
    suspend fun getSearchProduct(searchedQuery: String): Resource<SearchProductResponseModel>
    suspend fun getProduct(id: Int): Resource<ProductsItem>

    /**
     * Local
     */
    suspend fun getSavedProducts(search: String?): List<ProductsItem>
    suspend fun getSavedProduct(id: Int): ProductsItem
    suspend fun save(product: ProductsItem): Long
    suspend fun remove(product: ProductsItem): Int
}