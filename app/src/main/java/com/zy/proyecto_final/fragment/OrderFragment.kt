package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.constant.PaymentConstant
import com.zy.proyecto_final.databinding.FragmentOrderBinding
import com.zy.proyecto_final.databinding.FragmentProductDetailsBinding
import com.zy.proyecto_final.recyclerviewadapter.CarRecyclerViewAdapter
import com.zy.proyecto_final.recyclerviewadapter.OrderRecyclerViewAdapter
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.retrofit.entities.PaymentData
import com.zy.proyecto_final.viewmodel.OrderViewModel
import com.zy.proyecto_final.viewmodel.ProductViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import java.math.BigDecimal

class OrderFragment : Fragment() {
    private val viewmodel: OrderViewModel by activityViewModels<OrderViewModel>()
    private val productviewmodel: ProductViewModel by activityViewModels<ProductViewModel>()
    private val yingoviewmodel: YingoViewModel by activityViewModels<YingoViewModel>()
    private val userViewModel: UserViewModel by activityViewModels()
    private  lateinit var binding: FragmentOrderBinding
    private lateinit var txt_total : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnBuy: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        setupPaymentMethodAutoComplete()

        binding.infoAddress.setOnClickListener {
            val alertDialog = alertDialog()
            alertDialog.show()
        }

        binding.lifecycleOwner = this
        binding.userViewModel = userViewModel
        recyclerView = binding.recyclerViewOrder
        recyclerView.layoutManager =
            GridLayoutManager(context, 1)
        recyclerView.adapter =
            this.viewmodel.items.value?.let {
                OrderRecyclerViewAdapter(
                    it.toMutableList(), productviewmodel, requireContext()
                )
            }

        binding.buy.setOnClickListener {
            if (processPayment()) return@setOnClickListener
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupPaymentMethodAutoComplete() {
        val payment_methods = listOf(
            "ALIPAY",
            "WEIXIN",
            "BANK",
            "CASH",
            "UNIONPAY",
            "WALLET",
            "CARD",
            "PAYPAL",
            "APPLE_PAY",
            "ANDROID_PAY",
            "SAMSUNG_PAY",
            "PAYTM",
            "VENMO"
        )
        val autoComplete: AutoCompleteTextView = binding.payment
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            payment_methods
        )
        autoComplete.setAdapter(adapter)
        autoComplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position)
            Log.d("Selected Payment Method", selectedItem.toString())
        }
    }

    private fun processPayment(): Boolean {
        var paymentName = view?.findViewById<TextView>(R.id.payment)
        var paymentMethod = PaymentConstant.paymentMethodsMap[paymentName?.text.toString()]
        if (paymentMethod == null) {
            Toast.makeText(context, "Selecciona un metodo de pago", Toast.LENGTH_SHORT).show()
            return true
        }
        var amount: Double = view?.findViewById<TextView>(R.id.total)?.text.toString().toDouble()
        var paymentData = PaymentData(
            paymentMethod,
            amount
        )
        var code = yingoviewmodel.processPayment(paymentData)
        if (code == 0) {
            Toast.makeText(context, "Error al procesar el pago", Toast.LENGTH_SHORT).show()
        } else if (code == 1) {
            Toast.makeText(context, "Pago realizado", Toast.LENGTH_SHORT).show()
        }
        activity?.supportFragmentManager?.beginTransaction()?.replace(
            R.id.fragmentContainerView, MineFragment()
        )
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

        addressTextView.text = binding.address.toString()
        nameTextView.text = binding.name.toString()
        mobileTextView.text = binding.mobile.toString()
        dialogBuilder.setPositiveButton("Guardar"){
                dialog, _ ->
            // Aquí puedes agregar la lógica para guardar la información del usuario
            // Por ejemplo, puedes obtener los nuevos valores de los TextViews y actualizar el ViewModel
            val newAddress = addressTextView.text.toString()
            val newName = nameTextView.text.toString()
            val newMobile = mobileTextView.text.toString()

            binding.address.text = newAddress
            binding.name.text = newName
            binding.mobile.text = newMobile
        }
        dialogBuilder.setNegativeButton("Cancelar"){
                dialog, _ ->
            dialog.dismiss()
        }
        return alertDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData() // Move the loadData() call to onViewCreated
        var precio = BigDecimal.ZERO

        viewmodel.items.value?.let { items ->
            for (i in items) {
                i.product_id?.let { productId ->
                    val product = productviewmodel.getProductbyId(productId)
                    val productPrice = product?.price?.toBigDecimal() ?: BigDecimal.ZERO
                    precio += productPrice * (i.product_count?.toBigDecimal() ?: BigDecimal.ZERO)
                }
            }
        }
        view?.findViewById<TextView>(R.id.total)?.text = precio.toString()
    }
    private fun loadData() {
        viewmodel.items.value?.let {
            (view?.findViewById<RecyclerView>(R.id.recyclerView_order)!!.adapter as OrderRecyclerViewAdapter).setValues(
                it.toMutableList()
            )
        }
    }


}