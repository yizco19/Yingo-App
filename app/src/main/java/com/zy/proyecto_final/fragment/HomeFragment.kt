package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        offerViewModel.items.value?.forEach {
            //Comprueba si existe un producto con este offerId  si no hay no se añade
            if (productViewModel.items.value?.find { it.offerId == it.id } != null) {
                // agrega el imagen de producto y nombre
                for (i in productViewModel.items.value?.filter { it.offerId == it.id }!!) {
                    imageList.add(SlideModel(i.productPic, i.name, ScaleTypes.CENTER_INSIDE))
                }
            }
        }

        imageList.add(SlideModel(R.drawable.gaming, "Popular 1",ScaleTypes.CENTER_INSIDE))
        imageList.add(SlideModel(R.drawable.almacenamiento, "Popular 2",ScaleTypes.CENTER_INSIDE))
        imageList.add(SlideModel(R.drawable.monitor, "Popular 3",ScaleTypes.CENTER_INSIDE))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)
        return view
    }


}