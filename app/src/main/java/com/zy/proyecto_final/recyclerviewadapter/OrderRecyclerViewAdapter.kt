package com.zy.proyecto_final.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.databinding.FragmentItemorderBinding
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.viewmodel.ProductViewModel

class OrderRecyclerViewAdapter (private var orderList:List<Order>,
                                private val productviewmodel: ProductViewModel
) : RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderRecyclerViewAdapter.ViewHolder {
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
        val product = productviewmodel.getProductbyId(order.product_id!!)
        product?.imageUrl?.let { holder.productImage.setImageResource(it) }
        holder.productName.text = product?.name
        holder.productPrice.text = product?.price.toString()
        holder.productCount.text = order.product_count.toString()
        holder.productTotal.text= (product?.price?.times(order.product_count!!)).toString()

    }
    override fun getItemCount(): Int = orderList.size

    inner class ViewHolder(view: FragmentItemorderBinding) : RecyclerView.ViewHolder(view.root) {
        val productName = view.name
        val productPrice = view.price
        val productImage = view.productImg
        val productCount=view.count
        val productTotal=view.priceTotal


    }
    public fun setValues(v:MutableList<Order>){
        this.orderList=v;
        this.notifyDataSetChanged()
    }

}