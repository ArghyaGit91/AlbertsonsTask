package com.example.albertsonstask.presentation.di

import com.example.albertsonstask.BuildConfig
import com.example.albertsonstask.data.api.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_API)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }
}