package com.example.albertsonstask.data.repository.datasourceimpl

import com.example.albertsonstask.data.db.ProductDao
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.repository.datasource.ProductLocalDataSource
import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDataSource {

    override suspend fun getSavedProducts(category: String?): List<ProductsItem> {
        return category?.let {
            productDao.getSavedProducts(it)
        } ?: run {
            productDao.getSavedProducts()
        }
    }

    override suspend fun getSavedProduct(id: Int) = productDao.getSavedProduct(id)

    override suspend fun save(product: ProductsItem) = productDao.save(product)

    override suspend fun remove(product: ProductsItem) = productDao.remove(product)
}