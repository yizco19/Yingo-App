package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentProductDetailsBinding
import com.zy.proyecto_final.databinding.FragmentProductOfferDetailsBinding
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.zy.proyecto_final.util.PriceUtils
import com.zy.proyecto_final.viewmodel.OfferViewModel

class ProductDetailsFragment : Fragment() {
    private val viewmodel: ProductViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userviewmodel: UserViewModel by activityViewModels()
    private val favoritviewmodel: FavoriteViewModel by activityViewModels()
    private val offerviewmodel: OfferViewModel by activityViewModels()

    private lateinit var normalBinding: FragmentProductDetailsBinding
    private lateinit var offerBinding: FragmentProductOfferDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View
        val product = viewmodel.selectedproduct
        val offerId = product.offerId
        if (offerId != null && offerId != 0) {
            offerBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_offer_details,
                container,
                false
            )
            val offer = offerviewmodel.getOfferById(offerId.toInt())
            val precioConDescuento = PriceUtils.calculateDiscountedPrice(product, offer)
            offerBinding.price.text = precioConDescuento.toString()
            offerBinding.lifecycleOwner = this
            offerBinding.name.text = product.name
            offerBinding.description.text = product.description
            Glide.with(this)
                .load(product.productPic)
                .into(offerBinding.productPic)
            offerBinding.back.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, ProductsFragment())
                    ?.commit()
            }
            offerBinding.comprar.setOnClickListener {
                val car = Car(null, userviewmodel.userlogged.id, product.id, 1)
                carviewmodel.selectedcar = car
                carviewmodel.add()
                Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()
            }
            offerBinding.favorito.setOnClickListener {
                val favorite = Favorite(product.id, userviewmodel.userlogged.id, "defecto")
                favoritviewmodel.selectedfavorite = favorite
                favoritviewmodel.add()
                Toast.makeText(context, "Añadido al favorito", Toast.LENGTH_SHORT).show()
            }
            view = offerBinding.root
        } else {
            normalBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_details,
                container,
                false
            )
            normalBinding.price.text = product.price.toString()
            normalBinding.lifecycleOwner = this
            normalBinding.name.text = product.name
            normalBinding.description.text = product.description
            Glide.with(this)
                .load(product.productPic)
                .into(normalBinding.productPic)
            normalBinding.back.setOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView, ProductsFragment())
                    ?.commit()
            }
            normalBinding.comprar.setOnClickListener {
                val car = Car(null, userviewmodel.userlogged.id, product.id, 1)
                carviewmodel.selectedcar = car
                carviewmodel.add()
                Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()
            }
            normalBinding.favorito.setOnClickListener {
                val favorite = Favorite(product.id, userviewmodel.userlogged.id, "defecto")
                favoritviewmodel.selectedfavorite = favorite
                favoritviewmodel.add()
                Toast.makeText(context, "Añadido al favorito", Toast.LENGTH_SHORT).show()
            }
            view = normalBinding.root
        }

        return view
    }

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }
}
