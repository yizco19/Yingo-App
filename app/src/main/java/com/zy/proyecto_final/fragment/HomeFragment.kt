package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Product
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


        var productOfferList :List<Product> = productViewModel.getProductOfferList(6)

        for(product in productOfferList){
            imageList.add(SlideModel(product.productPic, product.name, ScaleTypes.FIT))
        }
        view.findViewById<ImageSlider>(R.id.image_slider).setImageList(imageList)
        view.findViewById<ImageSlider>(R.id.image_slider).setItemClickListener(object :
            ItemClickListener {
            override fun doubleClick(position: Int) {
                val item = productOfferList[position] // Obtener el producto seleccionado
                productViewModel.selectedproduct = item
                val fragment = ProductDetailsFragment.newInstance()
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, fragment)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }

            override fun onItemSelected(position: Int) {
                // You can listen here.
                val item = productOfferList[position] // Obtener el producto seleccionado
                productViewModel.selectedproduct = item
                val fragment = ProductDetailsFragment.newInstance()
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, fragment)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            } })

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
