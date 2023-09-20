package com.example.albertsonstask.domain.repository

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.utils.Resource

interface ProductRepository {
    suspend fun getSearchProduct(searchedQuery: String): Resource<SearchProductResponseModel>
    suspend fun getProduct(id: Int): Resource<ProductsItem>
}