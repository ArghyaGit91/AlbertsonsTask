package com.example.albertsonstask.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.albertsonstask.R
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.model.SearchProductResponseModel
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.domain.usecase.GetSearchedProductList
import com.example.albertsonstask.presentation.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun callSearchProduct(searchQuery: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            productsLiveData.postValue(Resource.Loading())
            if (Utils.isOnline(application)) {
                val searchedResultResponse = getSearchedProductList.execute(searchQuery)

                searchedResultResponse.let {
                    Log.v("product - apiResponse: ", searchedResultResponse.data.toString())
                    productsLiveData.postValue(searchedResultResponse)
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
            productsLiveData.postValue(Resource.Loading())
            if (Utils.isOnline(application)) {
                val searchedResultResponse = getSearchedProductList.execute(id)

                searchedResultResponse.let {
                    Log.v("product - apiResponse: ", searchedResultResponse.data.toString())
                    productLiveData.postValue(searchedResultResponse)
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
}