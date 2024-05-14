package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.recyclerviewadapter.CarRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.OrderViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class CarFragment : Fragment() {
    private val viewmodel: CarViewModel by activityViewModels<CarViewModel>()
    private val userviewmodel: UserViewModel by activityViewModels<UserViewModel>()
    private val productviewmodel: ProductViewModel by activityViewModels<ProductViewModel>()
    private val orderviewmodel: OrderViewModel by activityViewModels<OrderViewModel>()
    private val yingoviewmodel : YingoViewModel by activityViewModels<YingoViewModel>()
    var plus_click: ((Int, Car) -> Unit)? = null
    var minus_click: ((Int, Car) -> Unit)? = null
    private lateinit var txt_total :TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnPlaceOrder: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        view?.findViewById<RecyclerView>(R.id.recyclerView)!!?.layoutManager =
            GridLayoutManager(context, 1)
        view?.findViewById<RecyclerView>(R.id.recyclerView)?.adapter =
            this.viewmodel.items.value?.let { itemList ->
                CarRecyclerViewAdapter(
                    itemList.toMutableList(),
                    productviewmodel,
                    requireContext()
                )
            }
        (view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter as CarRecyclerViewAdapter).plus_click =
            { position: Int, item: Car ->
                run {
                    item.product_count = item.product_count + 1
                    this.viewmodel.update(item)
                    loadData()
                    //se avisa al principal
                    this.plus_click?.let { it -> it(position, item) }

                }
            }
        (view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter as CarRecyclerViewAdapter).minus_click =
            { position: Int, item: Car ->
                run {
                    item.product_count = item.product_count - 1
                    this.viewmodel.update(item)
                    //se avisa al principal
                    loadData()
                    this.minus_click?.let { it -> it(position, item) }
                }

            }
        view.findViewById<LinearLayout>(R.id.clear_cart).setOnClickListener {
            viewmodel.deleteAll()
            loadData()
        }
        txt_total=view.findViewById<TextView>(R.id.total)
        var btn_total=view.findViewById<TextView>(R.id.btn_total)
        setTotal(viewmodel.items.value!!)
        btn_total.setOnClickListener {
            //Pasa el carrito a YingoShop Services
            //Se avisa al principal
            yingoviewmodel.setCar(viewmodel.items.value!!)
            lifecycleScope.launch {
                if (viewmodel.items.value!!.isNotEmpty() && userviewmodel.userlogged != null) {
                    orderviewmodel.setAll(userviewmodel.userlogged!!, viewmodel.items.value!!)
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        setReorderingAllowed(true)
                        replace(
                            R.id.fragmentContainerView,
                            OrderFragment::class.java,
                            null,
                            "OrderFragment"
                        )
                    }
                    loadData()
                    parentFragmentManager.popBackStack()
                }
            }
        }
        return view
    }
    private fun setTotal(List: List<Car>) {
        var total = 0.0
        for (i in List) {
            var product_price = productviewmodel.getProductbyId(i.product_id!!)!!.price
            total +=i.product_count * product_price!!
        }
        txt_total.text = total.toString()
    }

    private fun loadData() {
        lifecycle.coroutineScope.launch {
            viewmodel.items.value?.let {
                (view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter as CarRecyclerViewAdapter).setValues(
                    it.toMutableList()
                )
            }
            setTotal(viewmodel.items.value!!)
        }
    }

}