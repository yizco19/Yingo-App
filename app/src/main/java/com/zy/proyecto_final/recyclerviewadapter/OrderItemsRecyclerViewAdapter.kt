package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemorderBinding
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.retrofit.entities.OrderItem
import com.zy.proyecto_final.viewmodel.ProductViewModel

class OrderItemsRecyclerViewAdapter (private var orderList:List<OrderItem>,
                                     private val productviewmodel: ProductViewModel,
                                     private val context: Context
) : RecyclerView.Adapter<OrderItemsRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsRecyclerViewAdapter.ViewHolder {
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
        val product = productviewmodel.getProductbyId(order.productId!!)
        Glide.with(this.context).load(product?.productPic).into(holder.productImage)
        holder.productName.text = product?.name
        holder.productPrice.text = product?.price.toString()
        holder.productCount.text = order.quantity.toString()
        holder.productTotal.text= (product?.price?.times(order.quantity!!)).toString()

    }
    override fun getItemCount(): Int = orderList.size

    inner class ViewHolder(view: FragmentItemorderBinding) : RecyclerView.ViewHolder(view.root) {
        val productName = view.name
        val productPrice = view.price
        val productImage = view.productImg
        val productCount=view.count
        val productTotal=view.priceTotal


    }
    public fun setValues(v:MutableList<OrderItem>){
        this.orderList=v;
        this.notifyDataSetChanged()
    }

}