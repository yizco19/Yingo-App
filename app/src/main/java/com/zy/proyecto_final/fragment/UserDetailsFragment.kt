package com.zy.proyecto_final.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel



class UserDetailsFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private val yingovieModel: YingoViewModel by activityViewModels()
    private var nickname: EditText? = null
    private var email: EditText? = null
    private var mobile: EditText? = null
    private var address: EditText? = null
    private var payment: EditText? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_details, container, false)
        view?.findViewById<Toolbar>(R.id.toolbar)!!.setNavigationOnClickListener {
            //replace fragment
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, MineFragment())?.commit()
        }
            if(viewModel.userlogged.id != null){
                nickname = view?.findViewById<EditText>(R.id.nickname)
                email = view?.findViewById<EditText>(R.id.email)
                mobile = view?.findViewById<EditText>(R.id.mobile)
                address = view?.findViewById<EditText>(R.id.address)
                //payment = view?.findViewById<EditText>(R.id.paymentMethod)
                //username?.setText(viewModel.userlogged.username)
                email?.setText(viewModel.userlogged.email)
                mobile?.setText(viewModel.userlogged.mobile)
                address?.setText(viewModel.userlogged.address)
                //payment?.setText(viewModel.userlogged.payment)

            }
         view?.findViewById<Button>(R.id.saveChanges)?.setOnClickListener {
             var user = viewModel.userlogged
             if(user.id != null){
                 var user = viewModel.userlogged
                 user.nickname = nickname?.text.toString()
                 user.email = email?.text.toString()
                 user.mobile = mobile?.text.toString()
                 user.address = address?.text.toString()
                 Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show()
                 // if ha cambiado de username va volver a login
                 /*if(user.username != usernametemp){

                     yingovieModel.updateUser(user)
                     viewModel.logout()
                     activity?.finish()
                     var intent = Intent(context, LoginActivity::class.java)
                     startActivity(intent)
                 }*/

                 viewModel.update(user)
                 //update service
                 yingovieModel.updateUser(user)
                 //cambia fragment a mine
                 activity?.supportFragmentManager?.beginTransaction()
                     ?.replace(R.id.fragmentContainerView, MineFragment())?.commit()
             } else{
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
             view?.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
                 ?.setNavigationOnClickListener {
                     requireActivity().onBackPressed()
                 }
        }


        // Inflate the layout for this fragment
        return view
    }

}