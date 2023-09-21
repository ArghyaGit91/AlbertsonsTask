package com.example.albertsonstask.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.albertsonstask.R
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.domain.usecase.GetSearchedProductList
import com.example.albertsonstask.presentation.utils.Utils
import com.example.albertsonstask.presentation.utils.validateSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val application: Application,
    private val getSearchedProductList: GetSearchedProductList
) : AndroidViewModel(application) {

    val productsLiveData: MutableLiveData<Resource<SearchProductResponseModel>> =
        MutableLiveData()

    val productLiveData: MutableLiveData<Resource<ProductsItem>> =
        MutableLiveData()

    val productsLoading = ObservableField(false)

    fun onSearch(charaSequence: CharSequence?) {
        val text = charaSequence.toString()
        if (text.validateSearch()) {
            callSearchProduct(text)
            getSavedProducts(text)
        }
    }

    var displaySavedList: Boolean = false

    fun displaySavedList() {
        displaySavedList = true
        getSavedProducts()
    }

    fun callSearchProduct(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            productsLoading.set(true)
            productsLiveData.postValue(Resource.Loading())
            if (Utils.isOnline(application)) {
                val searchedResultResponse = getSearchedProductList.execute(searchQuery)

                searchedResultResponse.let {
                    Log.v("product - apiResponse: ", searchedResultResponse.data.toString())
                    productsLiveData.postValue(searchedResultResponse)
                    productsLoading.set(false)
                }

            } else {
                productsLiveData.postValue(Resource.Error(application.getString(R.string.offline)))
                Log.e("product - errorResponse: ", application.getString(R.string.offline))
            }
        } catch (e: Exception) {
            productsLiveData.postValue(Resource.Error(application.getString(R.string.something_wrong)))
            Log.e("product - errorResponse: ", e.message.toString())
        }
    }

    fun callProduct(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            productLiveData.postValue(Resource.Loading())
            if (Utils.isOnline(application)) {
                val searchedResultResponse = getSearchedProductList.execute(id)

                searchedResultResponse.let {
                    Log.v("product - apiResponse: ", searchedResultResponse.data.toString())
                    productLiveData.postValue(searchedResultResponse)
                }

            } else {
                productLiveData.postValue(Resource.Error(application.getString(R.string.offline)))
                Log.e("product - errorResponse: ", application.getString(R.string.offline))
            }
        } catch (e: Exception) {
            productLiveData.postValue(Resource.Error(application.getString(R.string.something_wrong)))
            Log.e("product - errorResponse: ", e.message.toString())
        }
    }

    private val productsSavedState: MutableStateFlow<Resource<List<ProductsItem>>> =
        MutableStateFlow(Resource.Success(listOf()))

    val _productsSavedState: StateFlow<Resource<List<ProductsItem>>> = productsSavedState

    fun getSavedProducts(search: String? = null) = viewModelScope.launch {
        getSearchedProductList.getSavedProducts(search)
            .catch { e ->
                productsSavedState.value = Resource.Error(e.message)
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                listOf()
            ).collect {
                val response = it
                productsSavedState.value = Resource.Success(response)
            }
    }

    private val savedState: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Success(""))
    val _savedState = savedState

    fun save(product: ProductsItem) = viewModelScope.launch {
        getSearchedProductList.save(product)
            .catch { e ->
                savedState.value = Resource.Error(e.message)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                0L
            ).collect {
                val response = it
                if (response > 0) {
                    savedState.value = Resource.Success("saved")
                    Log.d("queryResponse", response.toString())
                }
            }
    }

    fun remove(product: ProductsItem) = viewModelScope.launch {
        getSearchedProductList.remove(product)
            .catch { e ->
                savedState.value = Resource.Error(e.message)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                0
            ).collect {
                val response = it
                if (response > 0) {
                    savedState.value = Resource.Success("removed")
                    Log.d("queryResponse", response.toString())
                }
            }
    }
}