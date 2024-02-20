package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentProductDetailsBinding
import com.zy.proyecto_final.fragments.CategoriesFragment
import com.zy.proyecto_final.viewmodel.ProductViewModel

class ProductDetailsFragment : Fragment() {
    private val viewmodel: ProductViewModel by activityViewModels()
    private val pedidoviewmodel: ProductViewModel by activityViewModels()
    private  lateinit var binding: FragmentProductDetailsBinding
    private var view:View?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        binding.lifecycleOwner = this
        binding.productViewModel = viewmodel

        binding.toolbar.setNavigationOnClickListener {
            //replace fragment
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, CategoriesFragment())?.commit()
        }
        binding.comprar.setOnClickListener {

        }
        // Inflate the layout for this fragment
        return binding.root


    }

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }
}