package com.example.menutoday.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menutoday.adapters.BillingProductsAdapter
import com.example.menutoday.data.order.OrderStatus
import com.example.menutoday.data.order.getOrderStatus
import com.example.menutoday.databinding.FragmentOrderDetailBinding
import com.example.menutoday.util.VerticalItemDecoration

class OrderDetailFragment: Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val billingProductsAdapter by lazy { BillingProductsAdapter() }
    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = args.order

        setupOrderRv()

        binding.apply {

            tvOrderId.text = "Order#${order.orderId}"

            stepView.setSteps(
                mutableListOf(
                    OrderStatus.Ordered.status,
                    OrderStatus.Confirmed.status,
                    OrderStatus.Delivering.status,
                    OrderStatus.Delivered.status,
                )
            )

            val currentOrderState = when(getOrderStatus(order.orderStatus)){
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Delivering -> 2
                is OrderStatus.Delivered -> 3
                else -> 0
            }

            stepView.go(currentOrderState,false)
            if (currentOrderState ==3) {
                stepView.done(true)
            }

            tvFullName.text = order.address.fullName
            tvAddress.text = "Table Number ${order.address.tableNumber}"
            tvPhoneNumber.text = order.address.phone

            tvTotalPrice.text = "₹ ${order.totalPrice}"

        }

        billingProductsAdapter.differ.submitList(order.products)
    }

    private fun setupOrderRv() {
        binding.rvProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }
}