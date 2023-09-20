package com.example.albertsonstask.data.api

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {

    @GET("products/search")
    suspend fun searchProduct(
        @Query("q")
        searchQuery: String
    ): Response<SearchProductResponseModel>

    @GET("/products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Response<ProductsItem>
}