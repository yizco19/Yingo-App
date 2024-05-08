package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.databinding.FragmentItemorderBinding
import com.zy.proyecto_final.databinding.FragmentItemordersBinding
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.viewmodel.ProductViewModel

class OrdersRecyclerViewAdapter (private var orderList:List<Order>,
                                 private var context: Context,
                                 private val orderStatus: String
) : RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder>() {
    var detail_click: ((Int, Order) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            FragmentItemordersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        holder.totalView.text = order.total.toString()
        holder.statusView.text = order.status
        holder.dateView.text = order.date
        holder.numberView.text = order.number.toString()
        holder.itemView.setOnClickListener {
            this.detail_click?.let { it -> it(position, order) }
        }

    }
    inner class ViewHolder(view: FragmentItemordersBinding) : RecyclerView.ViewHolder(view.root) {
        val totalView = view.total
        val statusView = view.status
        val dateView = view.date
        val numberView = view.number


    }

    override fun getItemCount(): Int = orderList.size
    public fun setValues(v:MutableList<Order>){
        this.orderList=v;
        this.notifyDataSetChanged()
    }

}