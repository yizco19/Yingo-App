import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.scwang.smart.refresh.header.BezierRadarHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentProductsBinding
import com.zy.proyecto_final.fragment.ProductDetailsFragment
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Favorite
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.recyclerviewadapter.ProductRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.util.DataObserver
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.CategoryViewModel
import com.zy.proyecto_final.viewmodel.FavoriteViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel



class ProductsFragment : Fragment() {
    private val viewmodel: ProductViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val favoritviewmodel: FavoriteViewModel by activityViewModels()
    private val categoryviewmodel: CategoryViewModel by activityViewModels()
    private val productviewmodel: ProductViewModel by activityViewModels()
    private val userviewmodel: UserViewModel by activityViewModels()
    private val yingomodel: YingoViewModel by activityViewModels()
    private lateinit var searchView: SearchView
    private var productAdapter: ProductRecyclerViewAdapter? = null
    private var productMutableList : MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProductsBinding.inflate(inflater, container, false)
        val refreshLayout: RefreshLayout =binding.refreshLayout

        refreshLayout.setRefreshHeader(BezierRadarHeader(requireContext()).setEnableHorizontalDrag(true))

        // Observa el LiveData de productos y actualiza el adaptador cuando cambie
        viewmodel.items.observe(viewLifecycleOwner) { products ->
            products?.let {
                productMutableList = it.toMutableList()
                productAdapter?.setValues(productMutableList)
            }
        }

        // Configura el adaptador del RecyclerView
        productAdapter = ProductRecyclerViewAdapter(mutableListOf(), requireContext())
        binding.listado.adapter = productAdapter

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
                val fragment = ProductDetailsFragment.newInstance()
                parentFragmentManager.commit {
                    replace(R.id.fragmentContainerView, fragment)
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
            }
        }

        binding.edFilter.addTextChangedListener { userFilter ->
            val filteredProducts = productMutableList.filter { product ->
                product.name!!.contains(userFilter.toString(), ignoreCase = true)
            }
            updateProducts(filteredProducts)
        }
        //get query
        val query = arguments?.getString("query")
        if(query!=null && query!=""){
            val filteredProducts = productMutableList.filter { product ->
                product.name!!.contains(query.toString(), ignoreCase = true)
            }
            updateProducts(filteredProducts)

        }
        return binding.root
    }

    private fun updateProducts(products: List<Product>) {
        productAdapter?.setValues(products.toMutableList())
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
