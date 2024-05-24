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
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel

class HomeFragment : Fragment() {

    private val offerViewModel: OfferViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val imageList = ArrayList<SlideModel>()

        productViewModel.items.observe(viewLifecycleOwner) { products ->
            offerViewModel.items.value?.forEach { offer ->
                val matchingProducts = products.filter { it.offerId == offer.id }
                matchingProducts.forEach { product ->
                    if (imageList.size < 6) {
                        imageList.add(SlideModel(product.productPic, product.name, ScaleTypes.CENTER_INSIDE))
                    }
                }
            }
            view.findViewById<ImageSlider>(R.id.image_slider).setImageList(imageList)
        }

        searchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                parentFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, ProductsFragment.newInstance(null, query)).commit()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return view
    }
}
