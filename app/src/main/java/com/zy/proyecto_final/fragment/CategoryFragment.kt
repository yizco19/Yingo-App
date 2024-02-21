package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentCategoryBinding
import com.zy.proyecto_final.fragments.ProductsFragment
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.recyclerviewadapter.CarRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.CategoryRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CategoryViewModel

/**
 * A fragment representing a list of Items.
 */
class CategoryFragment : Fragment() {
    private lateinit var binding : FragmentCategoryBinding
    private val viewmodel : CategoryViewModel by activityViewModels<CategoryViewModel>()
    private var view: View? = null;
    val content_click: ((Int, Category) -> Unit)? = null
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
        loadData()
        (view?.findViewById<RecyclerView>(R.id.listado)!!.adapter as CategoryRecyclerViewAdapter).content_click = { position:Int, item: Category ->
            run {
                this.viewmodel.selectedcategory=item
                //se avisa al principal
                this.content_click?.let { it -> it(position, item) }
                var  fm: FragmentManager = parentFragmentManager
                var f=fm.fragments
                fm.commit {
                    replace(R.id.fragmentContainerView, ProductsFragment.newInstance())
                }

            }
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