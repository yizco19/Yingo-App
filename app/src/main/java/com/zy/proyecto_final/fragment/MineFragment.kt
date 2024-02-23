package com.zy.proyecto_final.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.zy.proyecto_final.R
import com.zy.proyecto_final.activity.LoginActivity
import com.zy.proyecto_final.activity.UpdatePwdActivity
import com.zy.proyecto_final.pojo.Time
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        view?.findViewById<TextView>(R.id.username) !!.text = viewModel.userlogged.username
        // Iniciar la actualización periódica
        handler.postDelayed(updateTimeRunnable, delay)

        date = view?.findViewById<TextView>(R.id.date)!!
        time = view?.findViewById<TextView>(R.id.time)!!
        updateTimeRunnable.run()
        view?.findViewById<TextView>(R.id.logout) !!.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.logout()
                //cambia a welcome activity
                requireActivity().finish()

            }
        }
        view?.findViewById<RelativeLayout>(R.id.updatePwd) !!.setOnClickListener {
                var intent= Intent(context, UpdatePwdActivity::class.java)
            intent.putExtra("user_id",viewModel.userlogged.id)
                startActivityForResult(intent,1000)
        }
        return view
    }

    val updateTimeRunnable = object : Runnable {
        override fun run() {
            val timeCurrent: Time = timeViewModel.getTime()
            date!!.text = timeCurrent.date
            time!!.text = timeCurrent.time + ":" + timeCurrent.seconds

            // Programar la ejecución del Runnable nuevamente después del intervalo de tiempo
            handler.postDelayed(this, delay)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1000){
            activity?.finish()
            var intent= Intent(context, LoginActivity::class.java)
            //pasa userlogged
            startActivity(intent)
        }
    }
}
