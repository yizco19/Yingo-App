package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentOrderBinding
import com.zy.proyecto_final.recyclerviewadapter.OrderRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.utils.PriceUtils
import com.zy.proyecto_final.viewmodel.CarViewModel
import com.zy.proyecto_final.viewmodel.OfferViewModel
import com.zy.proyecto_final.viewmodel.OrderViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import java.math.BigDecimal

class OrderFragment : Fragment() {
    private val viewmodel: OrderViewModel by activityViewModels()
    private val productviewmodel: ProductViewModel by activityViewModels()
    private val yingoviewmodel: YingoViewModel by activityViewModels()
    private val carviewmodel: CarViewModel by activityViewModels()
    private val offerViewModel: OfferViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentOrderBinding
    private lateinit var txt_total: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBuy: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)

        binding.lifecycleOwner = this
        binding.userViewModel = userViewModel
        recyclerView = binding.recyclerViewOrder
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = viewmodel.items.value?.let {
            OrderRecyclerViewAdapter(
                it.toMutableList(), productviewmodel,
                offerViewModel = offerViewModel,
                requireContext()
            )
        }

        binding.buy.setOnClickListener {
            if (processPayment()) return@setOnClickListener
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData() // Move the loadData() call to onViewCreated
        calculateTotal()
    }

    private fun calculateTotal() {
        var precio = BigDecimal.ZERO

        viewmodel.items.value?.let { items ->
            for (i in items) {
                i.product_id?.let { productId ->
                    val product = productviewmodel.getProductbyId(productId)
                    if (product != null) {
                        val offer = product.offerId?.let { offerViewModel.getOfferById(it) }
                        val productPrice = PriceUtils.calculateDiscountedPrice(product, offer)?.toBigDecimal() ?: BigDecimal.ZERO
                        precio += productPrice * (i.product_count?.toBigDecimal() ?: BigDecimal.ZERO)
                    }
                }
            }
        }
        //format price 2 decimal
        val formattedPrice = String.format("%.2f", precio)
        view?.findViewById<TextView>(R.id.total)?.text = formattedPrice
    }

    private fun loadData() {
        viewmodel.items.value?.let {
            (view?.findViewById<RecyclerView>(R.id.recyclerView_order)!!.adapter as OrderRecyclerViewAdapter).setValues(
                it.toMutableList()
            )
        }
    }

    private fun processPayment(): Boolean {
        val paymentName = view?.findViewById<TextView>(R.id.payment)?.text.toString()
        val amount = view?.findViewById<TextView>(R.id.total)?.text.toString().toDoubleOrNull() ?: 0.0
        val phone = view?.findViewById<TextView>(R.id.mobile)?.text.toString()
        val name = view?.findViewById<TextView>(R.id.name)?.text.toString()
        val address = view?.findViewById<TextView>(R.id.address)?.text.toString()

        val paymentData = PaymentData(
            7,
            amount,
            phone,
            name,
            address
        )

        val result = yingoviewmodel.processPayment(paymentData)
        if (result?.code == 1) {
            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
            Log.d("Payment Error", result.message!!)
        } else if (result?.code == 0) {
            Toast.makeText(context, "Pago realizado", Toast.LENGTH_SHORT).show()
            viewmodel.clearOrder()
            carviewmodel.clearCart()
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                R.id.fragmentContainerView, MineFragment()
            )?.commit()
            return true
        }

        activity?.supportFragmentManager?.beginTransaction()?.replace(
            R.id.fragmentContainerView, MineFragment()
        )?.commit()
        return false
    }

    private fun alertDialog(): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Información de usuario")
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_edit_info_address, null)
        dialogBuilder.setView(dialogView)

        val alertDialog = dialogBuilder.create()

        val addressTextView = dialogView.findViewById<TextView>(R.id.dialog_address)
        val nameTextView = dialogView.findViewById<TextView>(R.id.dialog_name)
        val mobileTextView = dialogView.findViewById<TextView>(R.id.dialog_mobile)

        addressTextView.text = binding.address.text.toString()
        nameTextView.text = binding.name.text.toString()
        mobileTextView.text = binding.mobile.text.toString()

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Guardar") { _, _ ->
            val newAddress = addressTextView.text.toString()
            val newName = nameTextView.text.toString()
            val newMobile = mobileTextView.text.toString()

            binding.address.text = newAddress
            binding.name.text = newName
            binding.mobile.text = newMobile
        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        return alertDialog
    }
}
