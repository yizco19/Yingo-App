package com.zy.proyecto_final.fragment

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.viewModelScope
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.util.DataObserver
import com.zy.proyecto_final.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import java.io.File

class MineFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()

    private val yingoViewModel: YingoViewModel by activityViewModels<YingoViewModel>()
    private  val userViewModel :UserViewModel by activityViewModels<UserViewModel>()
    private var profile_avatar: ImageView? = null
    var date : TextView? = null
    var time : TextView? = null
    //private val timeViewModel: TimeViewModel by activityViewModels()
    //val delay: Long = 1000 // 1000 milisegundos = 1 segundo

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataObserver = DataObserver(this, yingoViewModel)

        dataObserver.observeUserData(userViewModel)

        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        view?.findViewById<TextView>(R.id.nickname) !!.text = viewModel.userlogged.username
        view.findViewById<TextView>(R.id.wallet_amount)!!.text = viewModel.userlogged.wallet.toString()
        // Iniciar la actualización periódica
        //handler.postDelayed(updateTimeRunnable, delay)

        //date = view?.findViewById<TextView>(R.id.date)!!
        //time = view?.findViewById<TextView>(R.id.time)!!
        //updateTimeRunnable.run()
        view.findViewById<TextView>(R.id.logout)!!.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.logout()
                //cambia a welcome activity
                requireActivity().finish()

            }
        }
        profile_avatar = view.findViewById<ImageView>(R.id.profile_avatar)
        profile_avatar!!.setOnClickListener{
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .galleryMimeTypes(  //Exclude gif images
                    mimeTypes = arrayOf(
                        "image/png",
                        "image/jpg",
                        "image/jpeg"
                    )
                )
                .start()
        }


        view.findViewById<TextView>(R.id.canjear)!!.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_redemption, null)
            val editTextRedeemCode = dialogView.findViewById<EditText>(R.id.editTextRedeemCode)

            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("Redimir código")
                setView(dialogView)
                setPositiveButton("Aceptar") { dialog, _ ->
                    val redeemCode = editTextRedeemCode.text.toString()
                    val result = yingoViewModel.redeemCode(redeemCode)
                        Toast.makeText(context, result!!.message, Toast.LENGTH_SHORT).show()
                    Log.d("Redeem", result.toString())
                    if(result.code == 0){
                        //actualizar wallet
                        viewModel.userlogged.wallet = viewModel.userlogged.wallet?.plus(result.data!!)
// Actualizar wallet con animación
                        animateTextChange(view.findViewById(R.id.wallet_amount), result.data.toString())
                    }

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
            R.id.pending to "PENDING",
            R.id.processing to "PROCESSING",
            R.id.shipping to "SHIPPING",
            R.id.delivered to "DELIVERED"
        )

        for ((viewId, orderStatus) in orderStatusMap) {
            view.findViewById<LinearLayout>(viewId)?.setOnClickListener {
                openOrder(orderStatus)
            }
        }
        view.findViewById<ImageView>(R.id.settings)?.setOnClickListener {
            var fm: FragmentManager = parentFragmentManager
            var f = fm.fragments
            fm.commit {
                replace(R.id.fragmentContainerView, SettingFragment.newInstance())

            }
        }
        return view
    }
    private fun animateTextChange(textView: TextView, newText: String) {
        val oldValue = textView.text.toString().toFloat()
        val newValue = newText.toFloat()

        val valueAnimator = ValueAnimator.ofFloat(oldValue, oldValue+newValue)
        valueAnimator.duration = 5000 // Duración de la animación en milisegundos
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()

        valueAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Float
            textView.text = String.format("%.2f", animatedValue)
        }

        valueAnimator.start()
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
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            profile_avatar!!.setImageURI(uri)
            // upload image a local

            //upload image a servidor
            val file = File(uri.path)


            yingoViewModel.uploadAvatar(uri)
            yingoViewModel.getUserData().observe(this) {
                if (it != null) {
                    userViewModel.update(it)
                    view?.findViewById<TextView>(R.id.wallet_amount) !!.text = viewModel.userlogged.wallet.toString()
                }
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
