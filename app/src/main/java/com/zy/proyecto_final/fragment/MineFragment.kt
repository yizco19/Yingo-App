package com.zy.proyecto_final.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
import androidx.lifecycle.viewModelScope
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.fragment.OrdersFragment
import com.zy.proyecto_final.viewmodel.TimeViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MineFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private val timeViewModel: TimeViewModel by activityViewModels()
    val handler = Handler()
    var date : TextView? = null
    var time : TextView? = null
    val delay: Long = 1000 // 1000 milisegundos = 1 segundo

    private val PICK_IMAGE_REQUEST = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        view?.findViewById<TextView>(R.id.username) !!.text = viewModel.userlogged.username
        // Iniciar la actualización periódica
        //handler.postDelayed(updateTimeRunnable, delay)

        //date = view?.findViewById<TextView>(R.id.date)!!
        //time = view?.findViewById<TextView>(R.id.time)!!
        //updateTimeRunnable.run()
        view?.findViewById<TextView>(R.id.logout) !!.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.logout()
                //cambia a welcome activity
                requireActivity().finish()

            }
        }

        view?.findViewById<LinearLayoutCompat>(R.id.wallet)!!.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_redemption, null)
            val editTextRedeemCode = dialogView.findViewById<EditText>(R.id.editTextRedeemCode)

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("Redimir código")
                setView(dialogView)
                setPositiveButton("Aceptar") { dialog, _ ->
                    val redeemCode = editTextRedeemCode.text.toString()
                    // Aquí puedes hacer lo que quieras con el código de redención ingresado
                    // Por ejemplo, enviarlo a un servidor para su validación o procesamiento
                    // Si deseas mostrar un mensaje de confirmación, puedes hacerlo aquí
                }
                setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        val orderStatusMap = mapOf(
            R.id.orderAll to "ALL",
            R.id.history to "HISTORY",
            R.id.pending to "PENDING",
            R.id.shipping to "DELIVERED"
        )

        for ((viewId, orderStatus) in orderStatusMap) {
            view?.findViewById<LinearLayout>(viewId)?.setOnClickListener {
                openOrder(orderStatus)
            }
        }
        view?.findViewById<ImageView>(R.id.settings)?.setOnClickListener {
            var fm: FragmentManager = parentFragmentManager
            var f = fm.fragments
            fm.commit {
                replace(R.id.fragmentContainerView, SettingFragment.newInstance())

            }
        }
        val profileImage = view.findViewById<ImageView>(R.id.image)

        // Agrega un OnClickListener al ImageView
        profileImage.setOnClickListener {
            // Abre el selector de imágenes
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
        return view
    }

    private fun openOrder(orderStatus: String) {
        var fm: FragmentManager = parentFragmentManager
        var f = fm.fragments
        fm.commit {
            replace(R.id.fragmentContainerView, OrdersFragment.newInstance(orderStatus))

        }
    }
    /*
        val updateTimeRunnable = object : Runnable {
            override fun run() {
                val timeCurrent: Time = timeViewModel.getTime()
                date!!.text = timeCurrent.date
                time!!.text = timeCurrent.time + ":" + timeCurrent.seconds

                // Programar la ejecución del Runnable nuevamente después del intervalo de tiempo
                handler.postDelayed(this, delay)
            }
        }*/

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
}
