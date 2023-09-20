package com.example.albertsonstask.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.databinding.ItemProductBinding

/**
 * @Author: Sourav PC
 * @Date: 20-09-2023
 */

class ProductAdapter(
    var products: List<ProductsItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(val binding : ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ProductsItem){
            binding.product = items
        }
    }

    init {
        Log.d("product - 2 adapter", products.toString())
//        onItemClick.invoke(1)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(products[position])
        holder.binding.root.setOnClickListener{
            onItemClick.invoke(position)
        }
    }

    override fun getItemCount()= products.size
}