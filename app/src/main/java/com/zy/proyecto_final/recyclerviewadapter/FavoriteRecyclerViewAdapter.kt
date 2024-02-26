package com.zy.proyecto_final.recyclerviewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.databinding.FragmentItemfavoriteBinding
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.viewmodel.ProductViewModel

class FavoriteRecyclerViewAdapter(
    private val values: MutableList<Favorite>,
    private val productViewModel: ProductViewModel
): RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {
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

        holder.contentView.text = product?.name
        holder.priceView.text = product?.price.toString()
        // Assuming detailView is an ImageView and you want to load an image from a URL
        // You need to use a proper image loading library like Picasso or Glide for production
        // This is just a placeholder assuming item.imageUrl is a drawable resource
        holder.detailView.setImageResource(product?.imageUrl ?: 0)

        holder.addView.setOnClickListener {
            add_click?.invoke(position, item!!)
        }
        holder.deleteView.setOnClickListener {
            delete_click?.invoke(position, item!!)
            notifyDataSetChanged()
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
}
