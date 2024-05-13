package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.constant.OrderConstant
import com.zy.proyecto_final.constant.getStatusString
import com.zy.proyecto_final.databinding.FragmentOrderDetailsBinding
import com.zy.proyecto_final.retrofit.YingoViewModel
import java.text.SimpleDateFormat
import com.zy.proyecto_final.recyclerviewadapter.OrderDetailRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

class OrderDetailsFragment : Fragment() {
    private val yingoViewModel: YingoViewModel by activityViewModels()
    private lateinit var binding: FragmentOrderDetailsBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val productviewmodel: ProductViewModel by activityViewModels()

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
                val user =userViewModel.getUserById(orderData.userId!!)
                binding.name.text= user!!.username
                binding.address.text=orderData.address
                binding.mobile.text=orderData.phone
               binding.address.text=orderData.address
                binding.title.title="Orden #${orderData.id} -  ${OrderConstant.getStatusString(
                    orderData.status!!
                )}"
                // solo me muestra la fecha
                // Crear un objeto SimpleDateFormat para formatear la fecha
                val formatoFecha = SimpleDateFormat("yyyy-MM-dd")
                val fecha = formatoFecha.format(orderData.createdAt!!)
                binding.orderDate.text=fecha
                binding.total.text=orderData.total.toString()

                binding.recyclerViewOrder.adapter = OrderDetailRecyclerViewAdapter(orderData.orderItems!!,productviewmodel, requireContext())
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
