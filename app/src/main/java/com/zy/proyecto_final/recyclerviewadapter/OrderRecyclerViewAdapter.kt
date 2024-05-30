package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemorderBinding
import com.zy.proyecto_final.pojo.Order
import com.zy.proyecto_final.retrofit.entities.OrderItem
import com.zy.proyecto_final.utils.PriceUtils
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import java.math.BigDecimal
import kotlin.time.times

class OrderRecyclerViewAdapter(
    private var orderList: List<Order>,
    private val productviewmodel: ProductViewModel,
    private val offerViewModel: OfferViewModel,
    private val context: Context
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
        order.product_id?.let { productId ->
            val product = productviewmodel.getProductbyId(productId)
            if (product != null) {
                Glide.with(context).load(product.productPic).into(holder.productImage)
                holder.productName.text = product.name

                val offer = product.offerId?.let { offerViewModel.getOfferById(it) }
                val precioConDescuento = PriceUtils.calculateDiscountedPrice(product, offer)
                holder.productPrice.text = "$" + String.format("%.2f", precioConDescuento)

                holder.productCount.text = order.product_count.toString()
                val productCount = order.product_count?:0
                holder.productTotal.text = "$" + String.format("%.2f", precioConDescuento * productCount)
            } else {
                // Manejar el caso en que el producto sea null
                holder.productName.text = "Producto no disponible"
                holder.productPrice.text = "$0.00"
                holder.productCount.text = "0"
                holder.productTotal.text = "$0.00"
            }
        } ?: run {
            // Manejar el caso en que product_id sea null
            holder.productName.text = "Producto no disponible"
            holder.productPrice.text = "$0.00"
            holder.productCount.text = "0"
            holder.productTotal.text = "$0.00"
        }
    }

    override fun getItemCount(): Int = orderList.size

    inner class ViewHolder(view: FragmentItemorderBinding) : RecyclerView.ViewHolder(view.root) {
        val productName = view.name
        val productPrice = view.price
        val productImage = view.productImg
        val productCount = view.count
        val productTotal = view.priceTotal
    }

    fun setValues(v: MutableList<Order>) {
        this.orderList = v
        notifyDataSetChanged()
    }
}
