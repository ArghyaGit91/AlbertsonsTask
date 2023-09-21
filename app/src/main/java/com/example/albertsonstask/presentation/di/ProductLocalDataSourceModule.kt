package com.example.albertsonstask.presentation.di

import com.example.albertsonstask.data.db.ProductDao
import com.example.albertsonstask.data.db.ProductDatabase
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
    fun providesProductDao(appDatabase: ProductDatabase): ProductDao {
        return appDatabase.productDao()
    }
}