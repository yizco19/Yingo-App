package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.zy.proyecto_final.R
import com.zy.proyecto_final.constant.OrderConstant
import com.zy.proyecto_final.constant.getStatusString
import com.zy.proyecto_final.databinding.FragmentOrderDetailsBinding
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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderId = arguments?.getInt(ARG_ORDER_ID)
        orderId?.let { id ->
            yingoViewModel.getOrderDetail(id).observe(viewLifecycleOwner) { orderData ->
                orderData?.let { data ->
                    // Actualizar la UI con los datos de la orden
                    data.userId?.let { userId ->
                        userViewModel.getUserById(userId)?.let { user ->
                            binding.name.text = user.username
                        } ?: run {
                            binding.name.text = "Usuario no encontrado"
                        }
                    } ?: run {
                        binding.name.text = "ID de usuario no disponible"
                    }

                    binding.address.text = data.address ?: "Dirección no disponible"
                    binding.mobile.text = data.phone ?: "Teléfono no disponible"
                    binding.title.title = "Orden #${data.id} - ${OrderConstant.getStatusString(data.status ?: 0)}"

                    // Crear un objeto SimpleDateFormat para formatear la fecha
                    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val fecha = data.createdAt?.let { formatoFecha.format(it) } ?: "Fecha no disponible"
                    binding.orderDate.text = fecha
                    binding.total.text = data.total?.toString() ?: "Total no disponible"
                } ?: run {
                    // Manejar el caso cuando orderData es null
                    binding.title.title = "Orden no disponible"
                }
            }
            yingoViewModel.getOrderItems(id).observe(viewLifecycleOwner) { orderItems ->
                // Manejar los elementos de la orden aquí
                // Puedes actualizar un RecyclerView o cualquier otro elemento de la UI
            }
        }
    }

    companion object {
        private const val ARG_ORDER_ID = "orderId"

        fun newInstance(order: OrderData): OrderDetailsFragment {
            val fragment = OrderDetailsFragment()
            val args = Bundle().apply {
                putInt(ARG_ORDER_ID, order.id!!)
                //put order object

            }
            fragment.arguments = args
            return fragment
        }
    }
}
