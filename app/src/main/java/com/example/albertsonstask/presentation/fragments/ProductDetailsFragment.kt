package com.example.albertsonstask.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.albertsonstask.databinding.FragmentProductDetailsBinding
import com.example.albertsonstask.presentation.viewmodel.ProductsViewModel

class ProductDetailsFragment : Fragment() {

    private val productsViewModel: ProductsViewModel by activityViewModels()
    private lateinit var binding: FragmentProductDetailsBinding
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = args.productId
        Log.d("product - 3 details", productId.toString())

        productsViewModel.callProduct(productId)

        initObservers()
        initViews()
    }

    private fun initObservers() {
        productsViewModel.productLiveData.observe(viewLifecycleOwner) {
            Log.d("product - 4 item", it.data.toString())
            binding.product = it.data
        }
    }

    private fun initViews() {

    }
}