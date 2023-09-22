package com.example.albertsonstask.presentation.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.model.searchResult
import com.example.albertsonstask.data.repository.ProductRepositoryImpl
import com.example.albertsonstask.data.repository.datasource.ProductRemoteDataSource
import com.example.albertsonstask.data.utils.Resource
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProductsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testDispatcher = StandardTestDispatcher()
    lateinit var productRemoteDataSource: ProductRemoteDataSource

    @get:Rule
    val instanceTaskExecutor = InstantTaskExecutorRule()

//    private val productsItem: ProductsItem

    @Mock
    lateinit var productRepositoryImpl: ProductRepositoryImpl


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)

        coEvery{(productRepositoryImpl.getSearchProduct("laptop"))} returns (
                Resource.Success(searchResult)
        )


    }

    @Test
    fun callSearchProduct() = runTest {
        Mockito.`when`(productRepositoryImpl.getSearchProduct("laptop"))
    }

    @Test
    fun callProduct() = runTest {
        Mockito.`when`(productRepositoryImpl.getProduct(1)).thenReturn(null)
//        val sut = ProductsViewModel()
    }

    @Test
    fun getSavedProducts() {
    }

    @Test
    fun save() {
    }

    @Test
    fun remove() {
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}