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
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.recyclerviewadapter.FavoriteRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.ProductRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FavoritesFragment : Fragment() {
    private val productviewmodel: ProductViewModel by activityViewModels()
    private val viewmodel: FavoriteViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private var view: View? = null
    var add_click: ((Int, Favorite) -> Unit)? = null
    var detail_click: ((Int, Favorite) -> Unit)? = null
    var delete_click: ((Int, Favorite) -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_favorites, container, false)
        view?.findViewById<Toolbar>(R.id.toolbar)!!.setNavigationOnClickListener {
            //replace fragment
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, HomeFragment())?.commit()
        }
        // Obtén el RecyclerView
        val recyclerView = view?.findViewById<RecyclerView>(R.id.listado)
        // Configura el LayoutManager
        recyclerView?.layoutManager = GridLayoutManager(context, 1)
        // Verifica si items es nulo antes de asignar el adaptador
        // Asigna el adaptador
        this.viewmodel.items.value?.let {
            recyclerView?.adapter = FavoriteRecyclerViewAdapter(it.toMutableList(), productviewmodel)
        }

        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as FavoriteRecyclerViewAdapter).add_click = { position:Int, item: Favorite ->
            run {
                var car :Car = Car(null,userViewModel.userlogged.id, item.product_id, 1)
                carviewmodel.selectedcar=car
                carviewmodel.add()
                Toast.makeText(context, "Añadido al carrito", Toast.LENGTH_SHORT).show()


            }
        }
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as FavoriteRecyclerViewAdapter).delete_click = { position:Int, item: Favorite ->
            run {
                viewmodel.selectedfavorite=item
        viewmodel.delete()
                this.delete_click?.let { it(position,item) }
                Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                //notify data change
                (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as FavoriteRecyclerViewAdapter).notifyDataSetChanged()
            }
        }


        return view
    }


}