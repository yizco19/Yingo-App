package com.zy.proyecto_final.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import com.zy.proyecto_final.R
import com.zy.proyecto_final.pojo.Time
import com.zy.proyecto_final.viewmodel.TimeViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MineFragment : Fragment() {
    private val viewModel: UserViewModel by activityViewModels()
    private val timeViewModel: TimeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine, container, false)
        view?.findViewById<TextView>(R.id.username) !!.text = viewModel.userlogged.username
                val time: Time = timeViewModel.getTime()
            view?.findViewById<TextView>(R.id.date) !!.text = time.date
            view?.findViewById<TextView>(R.id.time) !!.text = time.time

        view?.findViewById<TextView>(R.id.logout) !!.setOnClickListener {
            viewModel.viewModelScope.launch {
                viewModel.logout()
                //cambia a welcome activity
                requireActivity().finish()

            }
        }
        return view
    }
}
