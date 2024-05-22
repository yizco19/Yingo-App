package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemorderBinding
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.retrofit.entities.OrderItem
import com.zy.proyecto_final.viewmodel.ProductViewModel

class OrderItemsRecyclerViewAdapter(
    private var orderList: List<OrderItem>,
    private val productviewmodel: ProductViewModel,
    private val context: Context
) : RecyclerView.Adapter<OrderItemsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemorderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        Log.d("orderitemasdasd", order.toString())
        val product = productviewmodel.getProductbyId(order.productId!!)
        holder.productName.text = product?.name
        holder.productPrice.text = product?.price.toString()
        holder.productCount.text = order.quantity.toString()
        holder.productTotal.text = (product?.price?.times(order.quantity!!)).toString()
        Glide.with(this.context).load(product?.productPic).into(holder.productImage)
    }

    override fun getItemCount(): Int = orderList.size

    inner class ViewHolder(view: FragmentItemorderBinding) : RecyclerView.ViewHolder(view.root) {
        val productName: TextView = view.name
        val productPrice: TextView = view.price
        val productImage: ImageView = view.productImg
        val productCount: TextView = view.count
        val productTotal: TextView = view.priceTotal
    }

    fun setValues(orderItems: MutableList<OrderItem>) {
        this.orderList = orderItems
        Log.d("ordeQWEQEr", this.orderList.toString())
        notifyDataSetChanged()
    }
}
