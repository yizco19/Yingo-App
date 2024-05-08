package com.zy.proyecto_final.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.activity.UpdatePwdActivity
import com.zy.proyecto_final.viewmodel.UserViewModel

class SettingFragment  : Fragment(){
    private val viewModel: UserViewModel by activityViewModels()
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        view?.findViewById<RelativeLayout>(R.id.updatePwd) !!.setOnClickListener {
            var intent= Intent(context, UpdatePwdActivity::class.java)
            intent.putExtra("user_id",viewModel.userlogged.id)
            startActivityForResult(intent,1000)
        }
        view?.findViewById<RelativeLayout>(R.id.user_info) !!.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, UserDetailsFragment())?.commit()
        }
        return view
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1000){
            activity?.finish()
            var intent= Intent(context, LoginActivity::class.java)
            //pasa userlogged
            startActivity(intent)
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Obtiene la Uri de la imagen seleccionada
            val selectedImageUri: Uri? = data?.data

            // Asigna la Uri al ImageView
            view?.findViewById<ImageView>(R.id.image)?.setImageURI(selectedImageUri)
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return SettingFragment()

        }
    }

}