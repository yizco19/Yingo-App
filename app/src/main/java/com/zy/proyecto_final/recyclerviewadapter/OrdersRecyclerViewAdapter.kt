package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.constant.OrderConstant
import com.zy.proyecto_final.constant.getStatusString
import com.zy.proyecto_final.databinding.FragmentItemordersBinding
import com.zy.proyecto_final.retrofit.entities.OrderData
import java.text.SimpleDateFormat
import java.util.*

class OrdersRecyclerViewAdapter(
    private var orderList: List<OrderData>,
    private var context: Context,
    private val orderStatus: String
) : RecyclerView.Adapter<OrdersRecyclerViewAdapter.ViewHolder>() {

    var detail_click: ((Int, OrderData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
        holder.statusView.text = OrderConstant.getStatusString(order.status!!)

        // Formatear la fecha
        val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fecha = formatoFecha.format(order.createdAt)
        holder.dateView.text = fecha
        holder.numberView.text = order.id.toString()

        holder.itemView.setOnClickListener {
            this.detail_click?.let { it(position, order) }
        }
    }

    inner class ViewHolder(view: FragmentItemordersBinding) : RecyclerView.ViewHolder(view.root) {
        val totalView = view.total
        val statusView = view.status
        val dateView = view.date
        val numberView = view.orderNumber
    }

    override fun getItemCount(): Int = orderList.size

    fun setOrdersValues(v: MutableList<OrderData>) {
        this.orderList = v.sortedByDescending { it.createdAt }
        this.notifyDataSetChanged()
    }
}
