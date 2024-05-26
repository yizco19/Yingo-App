package com.zy.proyecto_final.fragment

import PolicyFragment
import UpdatePwdFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.viewmodel.UserViewModel

class SettingFragment  : Fragment(){
    private val viewModel: UserViewModel by activityViewModels()
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        view?.findViewById<TextView>(R.id.logout) !!.setOnClickListener {
            viewModel.logout()
            //cambia a welcome activity
            activity?.finish()
        }
        view?.findViewById<Toolbar>(R.id.toolbar) !!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, MineFragment())?.commit()

        }
        view?.findViewById<RelativeLayout>(R.id.updatePwd) !!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, UpdatePwdFragment())?.commit()
        }
        view?.findViewById<RelativeLayout>(R.id.user_info) !!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, UserDetailsFragment())?.commit()
        }

        view?.findViewById<RelativeLayout>(R.id.policy)!!.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, PolicyFragment())?.commit()
        }
        view?.findViewById<RelativeLayout>(R.id.about)!!.setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, AboutFragment())?.commit()
        }
        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Verifica si la contraseña se actualizó correctamente o no
        if (requestCode == 1000) {
            if (resultCode == 1000) {
                // La contraseña se actualizó correctamente, vuelve a iniciar sesión
                activity?.finish()
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                // Si el resultado no es 1000, no hagas nada o toma alguna otra acción
            }
        }
    }


    companion object {
        fun newInstance(): Fragment {
            return SettingFragment()

        }
    }

}