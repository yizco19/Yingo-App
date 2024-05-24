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
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import com.zy.proyecto_final.viewmodel.OfferViewModel

class ProductDetailsFragment : Fragment() {
    private val viewmodel: ProductViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userviewmodel: UserViewModel by activityViewModels()
    private val favoritviewmodel: FavoriteViewModel by activityViewModels()
    private  val offerviewmodel : OfferViewModel by activityViewModels()

    private  lateinit var binding: FragmentProductDetailsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(viewmodel.selectedproduct.offerId!=null && viewmodel.selectedproduct.offerId!!.toInt()!=0) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_offer_details,
                container,
                false
            )
            val offer = offerviewmodel.getOfferById(viewmodel.selectedproduct.offerId!!.toInt())
            val discount = offer!!.discount
            val precioConDescuento = viewmodel.selectedproduct.price?.times((1 - (discount?.div(100)!!)))
            binding.price.text=precioConDescuento.toString()
        }else{
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_details,
                container,
                false
            )
            binding.price.text=viewmodel.selectedproduct.price.toString()
        }
        binding.lifecycleOwner = this
        binding.name.text=viewmodel.selectedproduct.name

        binding.description.text=viewmodel.selectedproduct.description
// Asumiendo que productPic es una URL o una ruta de archivo
        Glide.with(this)
            .load(viewmodel.selectedproduct.productPic)
            .into(binding.productPic)
        binding.back.setOnClickListener {
            //replace fragment
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, ProductsFragment())?.commit()
        }
        binding.comprar.setOnClickListener {
            val product : Product = viewmodel.selectedproduct
            val user = userviewmodel.userlogged
            val car = Car( null, user.id, product.id,1)
            carviewmodel.selectedcar = car
            carviewmodel.add()
            Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()


        }
        binding.favorito.setOnClickListener {
            val product : Product = viewmodel.selectedproduct
            val favorite = Favorite(product.id, userviewmodel.userlogged.id,"defecto")
            favoritviewmodel.selectedfavorite = favorite
            favoritviewmodel.add()
            Toast.makeText(context, "Añadido al favorito", Toast.LENGTH_SHORT).show()
        }


        // Inflate the layout for this fragment
        return binding.root


    }

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }
}