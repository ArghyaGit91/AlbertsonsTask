package com.example.albertsonstask.data.repository.datasourceimpl

import com.example.albertsonstask.data.api.APIInterface
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.repository.datasource.ProductRemoteDataSource
import retrofit2.Response

class ProductRemoteDataSourceImpl(
    private val apiInterface: APIInterface
) : ProductRemoteDataSource {
    override suspend fun getSearchedProduct(searchedQuery: String): Response<SearchProductResponseModel> {
        return apiInterface.searchProduct(searchedQuery)
    }

    override suspend fun getProduct(id: Int): Response<ProductsItem> {
        return apiInterface.getProduct(id)
    }
}