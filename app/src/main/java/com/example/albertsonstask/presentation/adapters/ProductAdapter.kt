package com.example.albertsonstask.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.databinding.ItemProductBinding


class ProductAdapter(
    var products: List<ProductsItem>,
    var savedProducts: List<ProductsItem> = listOf(),
    private val onItemClick: ((Int) -> Unit)? = null,
    private val onSaveClick: ((ProductsItem, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(
        val binding: ItemProductBinding,
        private val savedProducts: List<ProductsItem>
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ProductsItem) {
            binding.product = items
            binding.saved = items in savedProducts
        }
    }

    init {
        Log.d("product - 2 adapter", products.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, savedProducts)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
        holder.binding.root.setOnClickListener {
            onItemClick?.invoke(products[position].id)
        }
        holder.binding.itemBody.ivSave.setOnClickListener {
            onSaveClick?.invoke(products[position], products[position] !in savedProducts)
        }
    }

    override fun getItemCount() = products.size
}