package com.zy.proyecto_final.fragments

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
import com.zy.proyecto_final.fragment.ProductDetailsFragment
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
    private var view: View? = null
    var fav_click: ((Int, Product) -> Unit)? = null
    var add_click: ((Int, Product) -> Unit)? = null
    var detail_click: ((Int, Product) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_products, container, false)
        // Obtén el RecyclerView
        val recyclerView = view?.findViewById<RecyclerView>(R.id.listado)
        // Configura el LayoutManager
        recyclerView?.layoutManager = GridLayoutManager(context, 2)
        // Verifica si items es nulo antes de asignar el adaptador
        // Asigna el adaptador
        this.viewmodel.items.value?.let {
            recyclerView?.adapter = ProductRecyclerViewAdapter(it.toMutableList())
        }
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductRecyclerViewAdapter).add_click = { position:Int, item: Product ->
            run {
                var car :Car = Car(null,userViewModel.userlogged.id, item.id, 1)
                carviewmodel.selectedcar=car
                carviewmodel.add()
                Toast.makeText(context,"Agregado al carrito [${item.name}]", Toast.LENGTH_SHORT).show()

            }
        }
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductRecyclerViewAdapter).fav_click = { position:Int, item: Product ->
            run {
                var favorite :Favorite = Favorite(item.id,userViewModel.userlogged.id,"defecto")
                favoritviewmodel.selectedfavorite=favorite
                favoritviewmodel.add()
                Toast.makeText(context, "Añadido al favorito [${item.name}]", Toast.LENGTH_SHORT).show()
            }
        }
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as ProductRecyclerViewAdapter).detail_click = { position:Int, item: Product ->
            run {
                this.viewmodel.selectedproduct=item
                //se avisa al principal
                this.detail_click?.let { it -> it(position, item) }
                var  fm: FragmentManager = parentFragmentManager
                var f=fm.fragments
                fm.commit {
                    replace(R.id.fragmentContainerView, ProductDetailsFragment.newInstance())

                }
            }
        }

        return view
    }


    companion object {
        private const val CATEGORY_ID_ARG = "CATEGORY_ID_ARG"

        fun newInstance(categoryId: Long?) = ProductsFragment().apply {
            arguments = Bundle().apply {
                putLong(CATEGORY_ID_ARG, categoryId ?: -1)
            }
        }
    }
}