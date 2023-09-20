package com.example.albertsonstask.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIInterfaceTest {
    private lateinit var service: APIInterface
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }

    private fun enqueueMockResponse(filName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(filName)
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()
        source?.let { mockResponse.setBody(it.readString(Charsets.UTF_8)) }
        server.enqueue(mockResponse)
    }

    @Test
    fun getSearchedRequestReceivedExpected() {
        runBlocking {
            enqueueMockResponse("searchedproductresponse.json")
            val responseBody = service.searchProduct("Laptop").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/products/search?q=Laptop")
        }
    }

    @Test
    fun getSearchedRequestReceivedPageSize() {

        runBlocking {
            enqueueMockResponse("searchedproductresponse.json")
            val responseBody = service.searchProduct("Laptop").body()
            val productList = responseBody?.products
            assertThat(productList?.size).isEqualTo(3)
        }
    }

    @Test
    fun getSearchedRequestReceivedResponseContent() {
        runBlocking {
            enqueueMockResponse("searchedproductresponse.json")
            val responseBody = service.searchProduct("Laptop").body()
            val productList = responseBody?.products
            val product = productList?.get(0)
            assertThat(product?.id).isNotNull()
            assertThat(product?.title).isNotNull()
            assertThat(product?.description).isNotNull()
            assertThat(product?.price).isNotNull()
            assertThat(product?.brand).isNotNull()
            assertThat(product?.thumbnail).isNotNull()

            assertThat(product?.id).isEqualTo(7)
            assertThat(product?.title).isEqualTo("Samsung Galaxy Book")
            assertThat(product?.description).isEqualTo("Samsung Galaxy Book S (2020) Laptop With Intel Lakefield Chip, 8GB of RAM Launched")
            assertThat(product?.price).isEqualTo(1499)
            assertThat(product?.brand).isEqualTo("Samsung")
            assertThat(product?.thumbnail).isEqualTo("https://i.dummyjson.com/data/products/7/thumbnail.jpg")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}