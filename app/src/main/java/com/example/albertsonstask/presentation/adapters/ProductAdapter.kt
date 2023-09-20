package com.example.albertsonstask.presentation.adapters

import android.util.Log
import com.example.albertsonstask.data.model.ProductsItem

/**
 * @Author: Sourav PC
 * @Date: 20-09-2023
 */

class ProductAdapter(
    private val products: List<ProductsItem>,
    private val onItemClick: (Int) -> Unit
) {
    init {
        Log.d("product - 2 adapter", products.toString())
        onItemClick.invoke(1)
    }
}