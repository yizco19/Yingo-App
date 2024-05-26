package com.zy.proyecto_final.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel



class AboutFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private val yingovieModel: YingoViewModel by activityViewModels()


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        view.findViewById<ImageView>(R.id.back)!!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, SettingFragment())?.commit()
        }
        val tvPolicyContent = view.findViewById<TextView>(R.id.tvDescription)
        val aboutContent = getString(R.string.about)
        // Convierte el contenido HTML a texto formateado
        tvPolicyContent.text = Html.fromHtml(aboutContent)
        // Inflate the layout for this fragment
        return view
    }

}