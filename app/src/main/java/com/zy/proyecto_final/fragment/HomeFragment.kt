package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Offer
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    private val offerViewModel: OfferViewModel by activityViewModels<OfferViewModel>()
    private val yingoViewModel: YingoViewModel by activityViewModels<YingoViewModel>()
    private val productViewModel: ProductViewModel by activityViewModels<ProductViewModel>()
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        // Inflate the layout for this fragment
        val imageList = ArrayList<SlideModel>() // Create image list

        offerViewModel.items.value?.forEach { offer ->
            // Comprueba si existe un producto con este offerId, si no hay, no se añade
            val matchingProducts = productViewModel.items.value?.filter { it.offerId == offer.id }

            if (!matchingProducts.isNullOrEmpty()) {
                // Agrega las imágenes del producto y el nombre
                matchingProducts.forEach { product ->
                    if (imageList.size < 6) {
                        imageList.add(SlideModel(product.productPic, product.name, ScaleTypes.CENTER_INSIDE))
                    } else {
                        // Salir del bucle si ya se han añadido 6 imágenes
                        return@forEach
                    }
                }
            }
        }
        view?.findViewById<SearchView>(R.id.search_view)




        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
        searchView = view.findViewById(R.id.search_view)
        // Configuración del SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    productViewModel.filter(newText)
                }
                return true
            }
        })
        return view
    }


}