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
    val recyclerViewLoad = ObservableField(false)
    val noDataFound = ObservableField(false)

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

    fun callSearchProduct(searchQuery: String) : MutableLiveData<Resource<SearchProductResponseModel>>{
        viewModelScope.launch(Dispatchers.IO){
            try {
                productsLoading.set(true)
                productsLiveData.postValue(Resource.Loading())
                if (Utils.isOnline(application)) {
                    val searchedResultResponse = getSearchedProductList.execute(searchQuery)

                    searchedResultResponse.let {
                        if (searchedResultResponse.data?.products?.isNotEmpty() == true) {
                            recyclerViewLoad.set(true)
                            noDataFound.set(false)
                            Log.v("product - apiResponse: ", searchedResultResponse.data.toString())
                            productsLiveData.postValue(searchedResultResponse)

                        }else{
                            recyclerViewLoad.set(false)
                            noDataFound.set(true)
                        }
                        productsLoading.set(false)
                    }

                } else {
                    productsLiveData.postValue(Resource.Error(application.getString(R.string.offline)))

                }
            } catch (e: Exception) {
                productsLiveData.postValue(Resource.Error(application.getString(R.string.something_wrong)))

            }
        }

        return productsLiveData

    }

    fun callProduct(id: Int) : MutableLiveData<Resource<ProductsItem>>{
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productLiveData.postValue(Resource.Loading())
                if (Utils.isOnline(application)) {
                    val searchedResultResponse = getSearchedProductList.execute(id)

                    searchedResultResponse.let {
                        productLiveData.postValue(searchedResultResponse)
                    }

                } else {
                    productLiveData.postValue(Resource.Error(application.getString(R.string.offline)))

                }
            } catch (e: Exception) {
                productLiveData.postValue(Resource.Error(application.getString(R.string.something_wrong)))

            }
        }

        return productLiveData
    }


    private val _productsSavedState: MutableStateFlow<Resource<List<ProductsItem>>> =
        MutableStateFlow(Resource.Success(listOf()))

    val productsSavedState: StateFlow<Resource<List<ProductsItem>>> = _productsSavedState

    fun getSavedProducts(search: String? = null) = viewModelScope.launch {
        getSearchedProductList.getSavedProducts(search)
            .catch { e ->
                _productsSavedState.value = Resource.Error(e.message)
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                null
            ).collect {
                it?.let { list ->
                    if (list.isNotEmpty()) {
                        recyclerViewLoad.set(true)
                        noDataFound.set(false)
                        _productsSavedState.value = Resource.Success(list)
                    }
                }
            }
    }

    private val _savedState: MutableStateFlow<Resource<String>> =
        MutableStateFlow(Resource.Success(""))
    val savedState = _savedState

    fun save(product: ProductsItem) = viewModelScope.launch {
        getSearchedProductList.save(product)
            .catch { e ->
                _savedState.value = Resource.Error(e.message)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                0L
            ).collect {
                val response = it
                if (response > 0) {
                    _savedState.value = Resource.Success("saved")
                    Log.d("queryResponse", response.toString())
                }
            }
    }

    fun remove(product: ProductsItem) = viewModelScope.launch {
        getSearchedProductList.remove(product)
            .catch { e ->
                _savedState.value = Resource.Error(e.message)
                Log.d("errorResponse", e.message.toString())
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                0
            ).collect {
                val response = it
                if (response > 0) {
                    _savedState.value = Resource.Success("removed")
                    Log.d("queryResponse", response.toString())
                }
            }
    }
}