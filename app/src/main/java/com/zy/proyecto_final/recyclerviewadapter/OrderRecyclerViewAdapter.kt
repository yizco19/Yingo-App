package com.zy.proyecto_final.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentItemorderBinding
import com.zy.proyecto_final.pojo.Order

class OrderRecyclerViewAdapter(private val productList: List<Order>) :
    RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>() {
    var add_click: ((Int, Order) -> Unit)? = null
    var onItemSelectedChanged: ((Boolean) -> Unit)? = null

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
        val product = productList[position]
        holder.bind(product)

    }

    override fun getItemCount(): Int = productList.size



    inner class ViewHolder(view: FragmentItemorderBinding) : RecyclerView.ViewHolder(view.root) {
        /*val productPrice: TextView = view.findViewById(R.id.price)
        val productName: TextView = view.findViewById(R.id.name)
        val productQuantity: TextView = view.findViewById(R.id.quantity)*/


        fun bind(order: Order) {
         /*   for ()
            productName.text = order.products.get(0).name
            productPrice.text = order.price.toString()
            checkBox.isChecked = product.isSelected*/
        }
    }
}