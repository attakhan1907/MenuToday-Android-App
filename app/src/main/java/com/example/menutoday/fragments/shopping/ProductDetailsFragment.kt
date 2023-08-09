package com.example.menutoday.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menutoday.R
import com.example.menutoday.adapters.ViewPager2Images
import com.example.menutoday.data.CartProduct
import com.example.menutoday.databinding.FragmentProductDetailsBinding
import com.example.menutoday.util.Resource
import com.example.menutoday.util.hideBottomNavigationView
import com.example.menutoday.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment: Fragment() {
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupViewpager()

        binding.imageClose.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonAddToCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product,1))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        binding.buttonAddToCart.startAnimation()
                    }

                    is Resource.Success ->{
                        binding.buttonAddToCart.revertAnimation()
                        binding.buttonAddToCart.setBackgroundColor(resources.getColor(R.color.black))
                    }

                    is Resource.Error ->{
                        binding.buttonAddToCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "₹ ${product.price}"
            tvProductDescription.text = product.description
        }

        viewPagerAdapter.differ.submitList(product.images)
    }

    private fun setupViewpager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

}