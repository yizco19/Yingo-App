package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemproductBinding
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.pojo.Product


class ProductRecyclerViewAdapter(
    private var values: MutableList<Product>,
    private val context: Context
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
        Glide.with(this.context).load(item.productPic).into(holder.detailView)
        holder.addView.setOnClickListener {
            this.add_click?.let { it -> it(position, item) }
        }
        holder.favView.setOnClickListener {
            this.fav_click?.let { it -> it(position, item) }
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
        val favView: Button = binding.favClick
        val addView: Button = binding.addClick




        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
    public fun setValues(v:MutableList<Product>){
        this.values=v;
        this.notifyDataSetChanged()
    }

}