package com.example.albertsonstask.presentation.di

import com.example.albertsonstask.domain.repository.ProductRepository
import com.example.albertsonstask.domain.usecase.GetSearchedProductList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetSearchedProductUseCase(
        productRepository: ProductRepository
    ): GetSearchedProductList {
        return GetSearchedProductList(productRepository)
    }
}