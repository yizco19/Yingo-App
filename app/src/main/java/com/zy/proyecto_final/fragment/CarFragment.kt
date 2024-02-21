package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Car
import com.zy.proyecto_final.pojo.Product
import com.zy.proyecto_final.recyclerviewadapter.CarRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.CategoryRecyclerViewAdapter
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.CategoryViewModel

class CarFragment : Fragment() {
    private val viewmodel: CarViewModel by activityViewModels<CarViewModel>()

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
        view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter =
            this.viewmodel.items.value?.let {
                CarRecyclerViewAdapter(
                    it.toMutableList()
                )
            }
        loadData()
        (view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter as CarRecyclerViewAdapter).plus_click =
            { position: Int, item: Car ->
                run {
                    item.product_count = item.product_count + 1
                    this.viewmodel.selectedcar = item
                    loadData()
                    //se avisa al principal
                    this.plus_click?.let { it -> it(position, item) }

                }
            }
        (view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter as CarRecyclerViewAdapter).minus_click =
            { position: Int, item: Car ->
                run {
                    item.product_count = item.product_count - 1
                    this.viewmodel.selectedcar = item
                    //se avisa al principal
                    loadData()
                    this.minus_click?.let { it -> it(position, item) }
                }

            }
        txt_total=view.findViewById<TextView>(R.id.total)
        var btn_total=view.findViewById<TextView>(R.id.btn_total)
        btn_total.setOnClickListener {

        }
        return view
    }
    private fun setTotal(List: List<Car>) {
        var total = 0.0
        for (i in List) {
            total +=i.product_count * i.product_price
        }
        txt_total.text = total.toString()
    }

    private fun loadData() {
        viewmodel.items.value?.let {
            (view?.findViewById<RecyclerView>(R.id.recyclerView)!!.adapter as CarRecyclerViewAdapter).setValues(
                it.toMutableList()
            )
        }
        setTotal(viewmodel.items.value!!)
    }

}