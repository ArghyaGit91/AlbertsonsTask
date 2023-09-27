package com.example.albertsonstask.presentation.di

import com.example.albertsonstask.data.api.APIInterface
import com.example.albertsonstask.data.repository.datasource.ProductRemoteDataSource
import com.example.albertsonstask.data.repository.datasourceimpl.ProductRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductRemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(
        apiInterface: APIInterface
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(apiInterface)
    }
}