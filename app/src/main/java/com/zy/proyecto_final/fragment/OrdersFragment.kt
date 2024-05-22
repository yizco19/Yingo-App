package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.constant.OrderConstant
import com.zy.proyecto_final.constant.getStatusValue
import com.zy.proyecto_final.recyclerviewadapter.OrdersRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel

class OrdersFragment : Fragment() {
    private val yingomodel: YingoViewModel by activityViewModels()
    private var ordersAdapter: OrdersRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_orders, container, false)

        val orderStatus = arguments?.getString("orderStatus")
        v.findViewById<Toolbar>(R.id.toolbar)?.title = orderStatus
        v.findViewById<RecyclerView>(R.id.listado).layoutManager = GridLayoutManager(context, 1)

        val ordersRecyclerViewAdapter = context?.let { OrdersRecyclerViewAdapter(mutableListOf(), it, orderStatus ?: "") }
        v.findViewById<RecyclerView>(R.id.listado).adapter = ordersRecyclerViewAdapter

        yingomodel.getOrders(OrderConstant.getStatusValue(orderStatus ?: "")!!)
        ordersRecyclerViewAdapter?.detail_click = { position, item ->
            val orderId = item.id
            if (orderId != null) {
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, OrderDetailsFragment.newInstance(item))
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            } else {
                Log.e("OrdersFragment", "Order ID is null")
            }
        }

        yingomodel.orders.observe(viewLifecycleOwner) {
            (v.findViewById<RecyclerView>(R.id.listado).adapter as OrdersRecyclerViewAdapter).setOrdersValues(it)
        }

        return v
    }

    companion object {
        fun newInstance(orderStatus: String): OrdersFragment {
            val fragment = OrdersFragment()
            val args = Bundle()
            args.putString("orderStatus", orderStatus)
            fragment.arguments = args
            return fragment
        }
    }
}