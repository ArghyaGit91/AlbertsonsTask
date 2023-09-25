package com.example.albertsonstask.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albertsonstask.R
import com.example.albertsonstask.data.model.ProductsItem
import com.example.albertsonstask.data.utils.Resource
import com.example.albertsonstask.databinding.FragmentProductHomeBinding
import com.example.albertsonstask.presentation.adapters.ProductAdapter
import com.example.albertsonstask.presentation.utils.Utils
import com.example.albertsonstask.presentation.viewmodel.ProductsViewModel
import kotlinx.coroutines.launch

class ProductHomeFragment : Fragment() {

    private val productsViewModel: ProductsViewModel by activityViewModels()
    private lateinit var binding: FragmentProductHomeBinding
    private var productAdapter: ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productSearchViewModel = productsViewModel
        initObservers()
        initViews()
    }

    private fun initObservers() {
        productsViewModel.productsLiveData.observe(viewLifecycleOwner) {
            it.data?.products?.let { products ->
                Log.d("product - 1 observer", products.toString())
                if (products.isNotEmpty()) {
                    productAdapter?.products = products
                    productAdapter?.notifyDataSetChanged()
                }else{
                    view?.let { Utils.hideSoftKeyBoard(requireContext(),it) }
                    Toast.makeText(requireContext(),"No data found",Toast.LENGTH_SHORT).show()
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            productsViewModel.productsSavedState.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { savedList ->
                            Log.e("product - saved list", savedList.toString())
                            productAdapter?.savedProducts = savedList
                            if (productsViewModel.displaySavedList) {
                                productAdapter?.products = savedList
                                productsViewModel.displaySavedList = false
                            }
                            productAdapter?.notifyDataSetChanged()
                        }
                    }

                    else -> {
                        it.message?.let { message ->
                            Log.e("product - error", message)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            productsViewModel.savedState.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let { saved ->
                            Log.e("product - $saved", saved)
                            productsViewModel.getSavedProducts()
                        }
                    }

                    else -> {
                        it.message?.let { message ->
                            Log.e("product - save error", message)
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        productAdapter = ProductAdapter(
            products = listOf(), onItemClick = { id: Int ->
                val bundle = Bundle()
                bundle.putInt("productId", id)
                findNavController().navigate(R.id.productDetailsFragment, bundle)

            }, onSaveClick = { product: ProductsItem, save: Boolean ->
                if (save) {
                    productsViewModel.save(product)
                } else {
                    productsViewModel.remove(product)
                }
            }
        )
        binding.rvProductList.layoutManager = LinearLayoutManager(activity)
        binding.rvProductList.adapter = productAdapter
    }
}