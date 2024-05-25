package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemproductBinding
import com.zy.proyecto_final.databinding.FragmentItemproductOfferBinding
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.utils.PriceUtils
import com.zy.proyecto_final.viewmodel.OfferViewModel

class ProductRecyclerViewAdapter(
    private var values: MutableList<Product>,
    private val context: Context,
    private val offerviewmodel: OfferViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var add_click: ((Int, Product) -> Unit)? = null
    var fav_click: ((Int, Product) -> Unit)? = null
    var detail_click: ((Int, Product) -> Unit)? = null

    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_OFFER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (values[position].offerId != null && values[position].offerId != 0) {
            VIEW_TYPE_OFFER
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_OFFER) {
            OfferViewHolder(
                FragmentItemproductOfferBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            NormalViewHolder(
                FragmentItemproductBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = values[position]
        if (holder is OfferViewHolder) {
            holder.contentView.text = item.name
            val offer = offerviewmodel.getOfferById(item.offerId!!)
            val precioConDescuento = PriceUtils.calculateDiscountedPrice(item, offer)
            Log.d("pricedesc", precioConDescuento.toString())
            Log.d("price", item.price.toString())
            holder.priceView.text = "$" + String.format("%.2f", precioConDescuento)
            Glide.with(this.context).load(item.productPic).into(holder.detailView)
            holder.addView.setOnClickListener {
                this.add_click?.let { it(position, item) }
            }
            holder.favView.setOnClickListener {
                this.fav_click?.let { it(position, item) }
            }
            holder.detailView.setOnClickListener {
                this.detail_click?.let { it(position, item) }
            }
        } else if (holder is NormalViewHolder) {
            holder.contentView.text = item.name
            holder.priceView.text = item.price.toString()
            Glide.with(this.context).load(item.productPic).into(holder.detailView)
            holder.addView.setOnClickListener {
                this.add_click?.let { it(position, item) }
            }
            holder.favView.setOnClickListener {
                this.fav_click?.let { it(position, item) }
            }
            holder.detailView.setOnClickListener {
                this.detail_click?.let { it(position, item) }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class NormalViewHolder(binding: FragmentItemproductBinding) :
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

    inner class OfferViewHolder(binding: FragmentItemproductOfferBinding) :
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

    fun setValues(v: MutableList<Product>) {
        this.values = v
        this.notifyDataSetChanged()
    }
}
