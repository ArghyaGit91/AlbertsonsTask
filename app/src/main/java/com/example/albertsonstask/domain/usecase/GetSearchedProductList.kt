package com.example.albertsonstask.domain.usecase

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSearchedProductList(
    private val productRepository: ProductRepository
) {
    suspend fun searchProductDetails(searchedQuery: String): Resource<SearchProductResponseModel> {
        return productRepository.getSearchProduct(searchedQuery)
    }

    suspend fun getProductDetails(id: Int): Resource<ProductsItem> {
        return productRepository.getProduct(id)
    }

    suspend fun getSavedProducts(search: String?): Flow<List<ProductsItem>> = flow {
        emit(productRepository.getSavedProducts(search))
    }.flowOn(Dispatchers.IO)

    suspend fun getSavedProduct(id: Int): Flow<ProductsItem> = flow {
        emit(productRepository.getSavedProduct(id))
    }.flowOn(Dispatchers.IO)

    suspend fun save(product: ProductsItem): Flow<Long> = flow {
        emit(productRepository.save(product))
    }.flowOn(Dispatchers.IO)

    suspend fun remove(product: ProductsItem): Flow<Int> = flow {
        emit(productRepository.remove(product))
    }.flowOn(Dispatchers.IO)
}