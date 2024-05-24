package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentCategoryBinding
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.recyclerviewadapter.CategoryRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val viewmodel: CategoryViewModel by activityViewModels()
    private val productviewmodel: ProductViewModel by activityViewModels()
    private var view: View? = null
    private var categoryAdapter: CategoryRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.listado.layoutManager = GridLayoutManager(context, 1)

        categoryAdapter = CategoryRecyclerViewAdapter(mutableListOf())
        binding.listado.adapter = categoryAdapter

        categoryAdapter?.content_click = { _, item ->
            parentFragmentManager.commit {
                if (item.id != 0) {
                    viewmodel.selectedcategory = item
                    productviewmodel.category_selected = viewmodel.getCategoryWithProducts()
                    replace(R.id.fragmentContainerView, ProductsFragment.newInstance(item.id))
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
            Toast.makeText(context, "Cargando productos de [${item.name}]", Toast.LENGTH_SHORT).show()
        }

        viewmodel.items.observe(viewLifecycleOwner) {
            categoryAdapter?.setValues(it)
            categoryAdapter?.notifyDataSetChanged()
        }

        return binding.root
    }
}
