package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.recyclerviewadapter.ProductRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

/**
 * A fragment representing a list of Items.
 */
class ProductsFragment : Fragment() {
    private val viewmodel: ProductViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val favoritviewmodel: FavoriteViewModel by activityViewModels()
    private var productAdapter: ProductRecyclerViewAdapter? = null
    private var view: View? = null
    var fav_click: ((Int, Product) -> Unit)? = null
    var add_click: ((Int, Product) -> Unit)? = null
    var detail_click: ((Int, Product) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_products, container, false)

        // Obtén el RecyclerView
        val recyclerView = binding.findViewById<RecyclerView>(R.id.listado)
        // Configura el LayoutManager
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Observa el LiveData de productos y actualiza el adaptador cuando cambie
        viewmodel.items.observe(viewLifecycleOwner) { products ->
            products?.let {
                productAdapter?.setValues(it.toMutableList())
            }
        }

        // Configura el adaptador del RecyclerView
        productAdapter = ProductRecyclerViewAdapter(mutableListOf(), requireContext())
        recyclerView.adapter = productAdapter

        // Manejar clics en elementos del RecyclerView
        productAdapter?.apply {
            add_click = { position, item ->
                val car = Car(null, userViewModel.userlogged.id, item.id, 1)
                carviewmodel.selectedcar = car
                carviewmodel.add()
                Toast.makeText(context,"Agregado al carrito [${item.name}]", Toast.LENGTH_SHORT).show()
            }

            fav_click = { position, item ->
                val favorite = Favorite(item.id, userViewModel.userlogged.id, "defecto")
                favoritviewmodel.selectedfavorite = favorite
                favoritviewmodel.add()
                Toast.makeText(context, "Añadido al favorito [${item.name}]", Toast.LENGTH_SHORT).show()
            }

            detail_click = { position, item ->
                viewmodel.selectedproduct = item
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, ProductDetailsFragment.newInstance())
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }

        return binding
    }

    companion object {
        private const val CATEGORY_ID_ARG = "CATEGORY_ID_ARG"

        fun newInstance(categoryId: Int?) = ProductsFragment().apply {
            arguments = Bundle().apply {
                putInt(CATEGORY_ID_ARG, categoryId ?: -1)
            }
        }
    }
}