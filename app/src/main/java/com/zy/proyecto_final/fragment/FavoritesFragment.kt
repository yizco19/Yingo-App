package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.recyclerviewadapter.FavoriteRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

class FavoritesFragment : Fragment() {
    private val productviewmodel: ProductViewModel by activityViewModels()
    private val viewmodel: FavoriteViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private var fragmentView: View? = null
    private lateinit var adapter: FavoriteRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_favorites, container, false)
        fragmentView?.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, HomeFragment())?.commit()
        }

        val recyclerView = fragmentView?.findViewById<RecyclerView>(R.id.listado)
        recyclerView?.layoutManager = GridLayoutManager(context, 1)

        adapter = FavoriteRecyclerViewAdapter(mutableListOf(), productviewmodel, requireContext())
        recyclerView?.adapter = adapter

        viewmodel.items.observe(viewLifecycleOwner) { favorites ->
            adapter.setValues(favorites.toMutableList())
        }

        adapter.add_click = { position, item ->
            val car = Car(null, userViewModel.userlogged.id, item.product_id, 1)
            carviewmodel.selectedcar = car
            carviewmodel.add()
            Toast.makeText(context, "Añadido al carrito", Toast.LENGTH_SHORT).show()
        }

        adapter.delete_click = { position, item ->
            viewmodel.selectedfavorite = item
            viewmodel.delete()
            Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
        }

        return fragmentView
    }
}
