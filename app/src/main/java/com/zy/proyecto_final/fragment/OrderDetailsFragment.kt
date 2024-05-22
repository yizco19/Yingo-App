package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.zy.proyecto_final.R
import com.zy.proyecto_final.constant.OrderConstant
import com.zy.proyecto_final.constant.getStatusString
import com.zy.proyecto_final.databinding.FragmentOrderDetailsBinding
import com.zy.proyecto_final.recyclerviewadapter.OrderItemsRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.OrderRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.OrdersRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.retrofit.entities.OrderData
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class OrderDetailsFragment : Fragment() {

    private val yingoViewModel: YingoViewModel by activityViewModels()
    private lateinit var binding: FragmentOrderDetailsBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private var order: OrderData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)
        val orderId = arguments?.getInt(ARG_ORDER_ID)
        orderId?.let { id ->
            // Verifica si ya existe un observador
            yingoViewModel.getOrderDetail(id).removeObservers(viewLifecycleOwner)
            yingoViewModel.getOrderDetail(id).observe(viewLifecycleOwner) { orderData ->
                Log.d("OrderDetailsFragment", "orderData: $orderData")
                updateUI(orderData)
            }
        }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.back.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun updateUI(orderData: OrderData?) {
        if (orderData != null) {
            orderData.userId?.let { userId ->
                userViewModel.getUserById(userId)?.let { user ->
                    binding.name.text = user.username
                } ?: run {
                    binding.name.text = "Usuario no encontrado"
                }
            } ?: run {
                binding.name.text = "ID de usuario no disponible"
            }

            binding.address.text = orderData.address ?: "Dirección no disponible"
            binding.mobile.text = orderData.phone ?: "Teléfono no disponible"
            binding.title.text = "Orden #${orderData.id} - ${OrderConstant.getStatusString(orderData.status ?: 0)}"
            if(orderData.payType==7){
                binding.payment.text = "Puntos"
            }else{
                binding.payment.text = "Metodo de pago desconocido"
            }

            val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fecha = orderData.createdAt?.let { formatoFecha.format(it) } ?: "Fecha no disponible"
            binding.orderDate.text = fecha
            binding.total.text = orderData.total?.toString() ?: "Total no disponible"

            if (orderData.orderItems != null) {
                binding.listado.layoutManager= GridLayoutManager(requireContext(), 1)
                binding.listado.adapter = context?.let {
                    OrderItemsRecyclerViewAdapter(orderData.orderItems, productViewModel, it)
                }
                binding.listado.adapter?.notifyDataSetChanged()



            } else {
                binding.listado.adapter = OrderItemsRecyclerViewAdapter(emptyList(), productViewModel, requireContext())
            }
        } else {
            binding.title.text = "Orden no disponible"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val ARG_ORDER_ID = "orderId"

        fun newInstance(order: OrderData): OrderDetailsFragment {
            val fragment = OrderDetailsFragment()
            val args = Bundle().apply {
                putInt(ARG_ORDER_ID, order.id!!)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
