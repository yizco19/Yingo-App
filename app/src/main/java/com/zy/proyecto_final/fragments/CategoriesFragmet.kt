package com.zy.proyecto_final.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentCategoryBinding
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.recyclerviewadapter.CategoryRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CategoryViewModel

/**
 * A fragment representing a list of Items.
 */
class CategoriesFragmet : Fragment() {
    private lateinit var binding : FragmentCategoryBinding
    private val viewmodel : CategoryViewModel by activityViewModels<CategoryViewModel>()
    private var view: View? = null;
    var add_cart_click: ((Int, Category) -> Unit)? = null
    var add_favorite_click: ((Int, Category) -> Unit)? = null
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_category, container, false)
        view?.findViewById<RecyclerView>(R.id.listado)!!?.layoutManager =
            GridLayoutManager(context, 1)
        view?.findViewById<RecyclerView>(R.id.listado)!!.adapter =
            this.viewmodel.items.value?.let {
                CategoryRecyclerViewAdapter(
                    it.toMutableList()
                )
            }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            CategoriesFragmet().apply {

            }
    }
}