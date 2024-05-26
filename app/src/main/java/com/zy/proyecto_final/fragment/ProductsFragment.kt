package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentProductsBinding
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.recyclerviewadapter.ProductRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.*

class ProductsFragment : Fragment() {
    private val viewmodel: ProductViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val favoritviewmodel: FavoriteViewModel by activityViewModels()
    private val categoryviewmodel: CategoryViewModel by activityViewModels()
    private val offerViewModel: OfferViewModel by activityViewModels()
    private lateinit var searchView: SearchView
    private var productAdapter: ProductRecyclerViewAdapter? = null
    private var productMutableList: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductsBinding.inflate(inflater, container, false)

        binding.listado.layoutManager = GridLayoutManager(context, 2)
        productAdapter = ProductRecyclerViewAdapter(mutableListOf(), requireContext(), offerviewmodel =offerViewModel )
        binding.listado.adapter = productAdapter

        val refreshLayout: RefreshLayout = binding.refreshLayout
        refreshLayout.setRefreshHeader(BezierRadarHeader(requireContext()).setEnableHorizontalDrag(true))

        productAdapter?.apply {
            add_click = { position, item ->
                val car = Car(null, userViewModel.userlogged.id, item.id, 1)
                carviewmodel.selectedcar = car
                carviewmodel.add()
                Toast.makeText(context, "Agregado al carrito [${item.name}]", Toast.LENGTH_SHORT).show()
            }

            fav_click = { position, item ->
                val favorite = Favorite(item.id, userViewModel.userlogged.id)
                favoritviewmodel.selectedfavorite = favorite
                favoritviewmodel.add()
                Log.d("favorito", item.toString())
                Toast.makeText(context, "Añadido al favorito ", Toast.LENGTH_SHORT).show()
            }

            detail_click = { position, item ->
                viewmodel.selectedproduct = item
                val fragment = ProductDetailsFragment.newInstance()
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, fragment)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }

        binding.edFilter.addTextChangedListener { userFilter ->
            viewmodel.items.value?.let { productMutableList = it.toMutableList() }
            val filteredProducts = productMutableList.filter { product ->
                product.name?.contains(userFilter.toString(), ignoreCase = true) == true
            }

            updateProducts(filteredProducts)
        }

        val query = arguments?.getString("query")
        if (!query.isNullOrBlank()) {
            Log.d("query", query)
            viewmodel.init(requireContext())

            val categoryId = arguments?.getInt(CATEGORY_ID_ARG) ?: -1
            viewmodel.items.observe(viewLifecycleOwner) { products ->
                productMutableList = products.toMutableList()

                binding.edFilter.setText(query)
                val filteredProducts = productMutableList.filter { product ->
                    product.name?.contains(query, ignoreCase = true) == true
                }
                updateProducts(filteredProducts)
            }
        }else{
            viewmodel.items.observe(viewLifecycleOwner) { products ->
                updateProducts(products)
            }
        }



        return binding.root
    }

    private fun updateProducts(products: List<Product>) {
        productAdapter?.setValues(products.toMutableList())
        productAdapter?.notifyDataSetChanged()
    }

    companion object {
        private const val CATEGORY_ID_ARG = "CATEGORY_ID_ARG"

        fun newInstance(categoryId: Int? = null, query: String? = null) = ProductsFragment().apply {
            arguments = Bundle().apply {
                putInt(CATEGORY_ID_ARG, categoryId ?: -1)
                putString("query", query)
            }
        }
    }
}
