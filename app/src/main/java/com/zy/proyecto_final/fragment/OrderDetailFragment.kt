package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentOrderDetailsBinding
import com.zy.proyecto_final.retrofit.YingoViewModel

class OrderDetailsFragment : Fragment() {
    private val yingoViewModel: YingoViewModel by activityViewModels()
    private lateinit var binding: FragmentOrderDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderId = arguments?.getInt(ARG_ORDER_ID)
        orderId?.let { id ->
            yingoViewModel.getOrderDetail(id).observe(viewLifecycleOwner) { orderData ->
                // Actualizar la UI con los datos de la orden
                binding.orderViewModel.selectedOrder = yingoViewModel.g
                binding.recyclerViewOrder.adapter = OrderDetailAdapter(orderData.products)
            }
        }
    }

    companion object {
        private const val ARG_ORDER_ID = "orderId"

        fun newInstance(orderId: Int): OrderDetailsFragment {
            val fragment = OrderDetailsFragment()
            val args = Bundle().apply {
                putInt(ARG_ORDER_ID, orderId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
