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
import com.zy.proyecto_final.databinding.FragmentCategoryBinding
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.recyclerviewadapter.CategoryRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel

/**
 * A fragment representing a list of Items.
 */
class CategoryFragment : Fragment() {
    private lateinit var binding : FragmentCategoryBinding
    private val viewmodel : CategoryViewModel by activityViewModels<CategoryViewModel>()
    private val productviewmodel : ProductViewModel by activityViewModels<ProductViewModel>()
    private var view: View? = null;
    private var categoryAdapter: CategoryRecyclerViewAdapter? = null // Adaptador para el RecyclerView

    val content_click: ((Int, Category) -> Unit)? = null
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        view = binding.root

        // Configuración del RecyclerView
        categoryAdapter = CategoryRecyclerViewAdapter(mutableListOf())
        binding.listado.layoutManager = GridLayoutManager(context, 1)
        binding.listado.adapter = categoryAdapter

        // Observa el LiveData de categorías y actualiza el adaptador cuando cambie
        viewmodel.items.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categoryAdapter?.setValues(it.toMutableList())
            }
        }

        // Manejar el clic en los elementos del RecyclerView
        categoryAdapter?.content_click = { position, item ->
            viewmodel.selectedcategory = item
            productviewmodel.category_selected = viewmodel.getCategoryWithProducts()
            parentFragmentManager.commit {
                replace(R.id.fragmentContainerView, ProductsFragment.newInstance(item.id))
                setReorderingAllowed(true)
                addToBackStack(null)
            }
            Toast.makeText(context, "Cargando productos de [${item.name}]", Toast.LENGTH_SHORT).show()
        }

        return view
    }
    private fun loadData() {
        viewmodel.items.value?.let {
            (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as CategoryRecyclerViewAdapter).setValues(
                it.toMutableList()
            )
        }
    }
}