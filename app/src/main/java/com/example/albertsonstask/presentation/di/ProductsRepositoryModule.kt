package com.example.albertsonstask.presentation.di

import com.example.albertsonstask.data.repository.ProductRepositoryImpl
import com.example.albertsonstask.data.repository.datasource.ProductLocalDataSource
import com.example.albertsonstask.data.repository.datasource.ProductRemoteDataSource
import com.example.albertsonstask.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductsRepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        productRemoteDataSource: ProductRemoteDataSource,
        productLocalDataSource: ProductLocalDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(productRemoteDataSource, productLocalDataSource)
    }
}