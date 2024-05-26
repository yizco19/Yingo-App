package com.zy.proyecto_final.recyclerviewadapter

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zy.proyecto_final.databinding.FragmentItemcarBinding
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.utils.PriceUtils
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel

class CarRecyclerViewAdapter(private var carList: List<Car>,
                             private val productViewModel: ProductViewModel,
                             private val offerViewModel: OfferViewModel,
                             private val context: Context
) :
    RecyclerView.Adapter<CarRecyclerViewAdapter.ViewHolder>() {
    var plus_click: ((Int, Car) -> Unit)? = null
    var minus_click: ((Int, Car) -> Unit)? = null
    var onItemSelectedChanged: ((Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarRecyclerViewAdapter.ViewHolder {
       return ViewHolder(
            FragmentItemcarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val car = carList[position]
        val product = productViewModel.getProductbyId(car.product_id!!)
        product?.productPic?.let { urlOrFilePath ->
            Glide.with(context)
                .load(urlOrFilePath)
                .into(holder.productImage)
        }
        holder.productName.text = product?.name
        //comprueba si el producto tiene oferta
        var offer = offerViewModel.getOfferById(product!!.offerId!!)
        var precioConDescuento = PriceUtils.calculateDiscountedPrice(product,offer)
        holder.productPrice.text = "$" + String.format("%.2f", precioConDescuento)
        holder.productCount.text = car.product_count.toString()
        holder.btn_plus.setOnClickListener {
            plus_click?.let { it1 -> it1(position, car) }
        }
        holder.btn_minus.setOnClickListener {
            minus_click?.let { it1 -> it1(position, car) }
        }

    }

    override fun getItemCount(): Int = carList.size



    inner class ViewHolder(view: FragmentItemcarBinding) : RecyclerView.ViewHolder(view.root) {
        val productName = view.name
        val productPrice = view.price
        val productImage = view.productImg
        val productCount=view.productCount
        var btn_plus:TextView=view.btnPlus
        val btn_minus:TextView=view.btnMinus


    }
    public fun setValues(v: MutableList<Car>) {
        this.carList = v
        this.notifyDataSetChanged()
    }

}

