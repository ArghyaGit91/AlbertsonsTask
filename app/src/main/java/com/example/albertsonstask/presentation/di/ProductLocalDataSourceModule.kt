package com.example.albertsonstask.presentation.di

import com.example.albertsonstask.data.db.ProductDao
import com.example.albertsonstask.data.db.ProductDatabase
import com.example.albertsonstask.data.repository.datasource.ProductLocalDataSource
import com.example.albertsonstask.data.repository.datasourceimpl.ProductLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductLocalDataSourceModule {

    @Singleton
    @Provides
    fun provideProductLocalDataSource(
        productDao: ProductDao
    ): ProductLocalDataSource {
        return ProductLocalDataSourceImpl(productDao)
    }
}