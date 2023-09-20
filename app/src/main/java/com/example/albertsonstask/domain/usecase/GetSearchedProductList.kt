package com.example.albertsonstask.domain.usecase

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.domain.repository.ProductRepository

class GetSearchedProductList(
    private val productRepository: ProductRepository
) {
    suspend fun execute(searchedQuery: String): Resource<SearchProductResponseModel> {
        return productRepository.getSearchProduct(searchedQuery)
    }

    suspend fun execute(id: Int): Resource<ProductsItem> {
        return productRepository.getProduct(id)
    }
}