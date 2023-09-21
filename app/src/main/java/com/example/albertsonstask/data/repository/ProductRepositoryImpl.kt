package com.example.albertsonstask.data.repository

import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.repository.datasource.ProductLocalDataSource
import com.example.albertsonstask.data.repository.datasource.ProductRemoteDataSource
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productLocalDataSource: ProductLocalDataSource
) : ProductRepository {

    /**
     * Remote
     */
    override suspend fun getSearchProduct(searchedQuery: String): Resource<SearchProductResponseModel> {
        val response = productRemoteDataSource.getSearchedProduct(searchedQuery)

        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getProduct(id: Int): Resource<ProductsItem> {
        val response = productRemoteDataSource.getProduct(id)

        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    /**
     * Local
     */

    override suspend fun getSavedProducts(search: String?): List<ProductsItem> {
        return productLocalDataSource.getSavedProducts(search)
    }

    override suspend fun getSavedProduct(id: Int): ProductsItem {
        return productLocalDataSource.getSavedProduct(id)
    }

    override suspend fun save(product: ProductsItem): Long {
        return productLocalDataSource.save(product)
    }

    override suspend fun remove(product: ProductsItem): Int {
        return productLocalDataSource.remove(product)
    }
}