package com.example.albertsonstask.presentation.viewmodel

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.albertsonstask.data.api.APIInterface
import com.example.albertsonstask.data.db.ProductDao
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.model.searchResult
import com.example.albertsonstask.data.repository.ProductRepositoryImpl
import com.example.albertsonstask.data.repository.datasource.ProductLocalDataSource
import com.example.albertsonstask.data.repository.datasource.ProductRemoteDataSource
import com.example.albertsonstask.data.repository.datasourceimpl.ProductLocalDataSourceImpl
import com.example.albertsonstask.data.repository.datasourceimpl.ProductRemoteDataSourceImpl
import com.example.albertsonstask.data.utils.MainCoroutineRule
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.domain.repository.ProductRepository
import com.example.albertsonstask.domain.usecase.GetSearchedProductList
import com.example.albertsonstask.presentation.utils.Utils
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalCoroutinesApi::class)
internal class ProductsViewModelTest {


    @get:Rule
    val instanceTaskExecutor = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    private lateinit var productsViewModel: ProductsViewModel




    @MockK
    private lateinit var application: Application

    @MockK
    private lateinit var getSearchedProductList: GetSearchedProductList

    @MockK
    private lateinit var productRepository: ProductRepositoryImpl

    @MockK
    private lateinit var productRemoteDataSource: ProductRemoteDataSourceImpl

    @MockK
    private lateinit var productLocalDataSource: ProductLocalDataSourceImpl

    @MockK
    private lateinit var apiInterface: APIInterface

    @MockK
    private lateinit var productDao: ProductDao



    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        productsViewModel = spyk(ProductsViewModel(application,getSearchedProductList))
        getSearchedProductList = spyk(GetSearchedProductList(productRepository))
        productRepository = spyk(ProductRepositoryImpl(productRemoteDataSource,productLocalDataSource))
        productLocalDataSource = spyk(ProductLocalDataSourceImpl(productDao))
        productRemoteDataSource = spyk(ProductRemoteDataSourceImpl(apiInterface))


    }



    @Test
    fun when_callSearchProduct_called_and_then_return_success_response() = runTest {

        val responseSuccess = SearchProductResponseModel(
            0,0,0, listOf(
                ProductsItem(0.0,"", listOf(),0,0.0,"",0,"",
                    0,"laptops","",false)
            )
        )
        coEvery{
//            (productsViewModel.callSearchProduct("laptop"))

            productRepository.getSearchProduct("laptop")
        }returns Resource.Success(responseSuccess)
//        productsViewModel.callSearchProduct("laptop")
        coVerify {
//            productsViewModel.productsLiveData.value
            productRepository.getSearchProduct("laptop")
        }

        assertEquals(Resource.Success(responseSuccess),productsViewModel.productsLiveData.value)

    }




    @Test
    fun when_call_Product_called_and_then_return_success_response() = runTest {
        coEvery{(productsViewModel.callProduct(7))}
    }




    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}