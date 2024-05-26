package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemfavoriteBinding
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.viewmodel.ProductViewModel

class FavoriteRecyclerViewAdapter(
    private var values: MutableList<Favorite>,
    private val productViewModel: ProductViewModel,
    private val context: Context
) : RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {

    var add_click: ((Int, Favorite) -> Unit)? = null
    var delete_click: ((Int, Favorite) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentItemfavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val product = productViewModel.getProductbyId(item.product_id ?: 0)

        if (product != null) {
            holder.contentView.text = product.name
            holder.priceView.text = product.price.toString()
            Glide.with(this.context)
                .load(product.productPic)
                .into(holder.detailView)

            holder.addView.setOnClickListener {
                add_click?.invoke(position, item)
            }
            holder.deleteView.setOnClickListener {
                delete_click?.invoke(position, item)
            }
        } else {
            holder.contentView.text = "Producto no encontrado"
            holder.priceView.text = ""
            holder.detailView.setImageResource(android.R.color.transparent)
            holder.addView.setOnClickListener(null)
            holder.deleteView.setOnClickListener(null)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemfavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.name
        val priceView: TextView = binding.price
        val detailView: ImageView = binding.productImg
        val addView: ImageView = binding.btnAdd
        val deleteView: ImageView = binding.btnDelete

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    fun setValues(newValues: MutableList<Favorite>) {
        values = newValues
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if (position in values.indices) {
            values.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
