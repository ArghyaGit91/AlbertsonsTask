package com.example.albertsonstask.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.albertsonstask.R
import com.example.albertsonstask.databinding.FragmentProductHomeBinding
import com.example.albertsonstask.presentation.adapters.ProductAdapter
import com.example.albertsonstask.presentation.utils.validateSearch
import com.example.albertsonstask.presentation.viewmodel.ProductsViewModel

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

        initObservers()
        initViews()
    }

    private fun initObservers() {
        productsViewModel.productsLiveData.observe(viewLifecycleOwner) {
            it.data?.products?.let { products ->
                Log.d("product - 1 observer", products.toString())
                productAdapter?.products = products
                productAdapter?.notifyDataSetChanged()
            }
        }
    }

    private fun initViews() {
        productAdapter = ProductAdapter(listOf()){id: Int ->
            val bundle = Bundle()
            bundle.putInt("productId", id)
            findNavController().navigate(R.id.productDetailsFragment, bundle)

            /*findNavController().navigate(
                ProductHomeFragmentDirections
                    .actionProductHomeFragmentToProductDetailsFragment(id)
            )*/
        }


        binding.rvProductList.layoutManager = LinearLayoutManager(activity)
        binding.rvProductList.adapter = productAdapter
    }
}