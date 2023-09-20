package com.example.albertsonstask.data.repository.datasource

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import retrofit2.Response

interface ProductRemoteDataSource {
    suspend fun getSearchedProduct(searchedQuery: String): Response<SearchProductResponseModel>
    suspend fun getProduct(id: Int): Response<ProductsItem>
}