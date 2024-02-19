package com.zy.proyecto_final.recyclerviewadapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zy.proyecto_final.databinding.FragmentItemproductBinding

import com.zy.proyecto_final.databinding.FragmentProductsBinding
import com.zy.proyecto_final.pojo.Product

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ProductRecyclerViewAdapter(
    private val values: MutableList<Product>
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {
    var add_click: ((Int, Product) -> Unit)? = null
    var fav_click: ((Int, Product) -> Unit)? = null
    var detail_click: ((Int, Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemproductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
        holder.priceView.text = item.price.toString()
        holder.contentView.setOnClickListener {
            this.add_click?.let { it -> it(position, item) }
        }
        holder.priceView.setOnClickListener {
            this.add_click?.let { it -> it(position, item) }
        }
        holder.detailView.setOnClickListener {
            this.detail_click?.let { it -> it(position, item) }
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemproductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        val priceView: TextView = binding.price
        val detailView: ImageView = binding.detail

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}