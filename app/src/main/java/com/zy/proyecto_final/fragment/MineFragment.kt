package com.zy.proyecto_final.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.FragmentMineBinding
import com.zy.proyecto_final.pojo.Category
import com.zy.proyecto_final.viewmodel.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MineFragment : Fragment() {
    private lateinit var binding : FragmentMineBinding
    private val viewmodel : UserViewModel by activityViewModels<UserViewModel>()
    private var view: View? = null;
    val content_click: ((Int, Category) -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var imagen: ImageView? =view?.findViewById<ImageView>(R.id.image)
        imagen.setOnClickListener{
            pickImage.launch(imagen)
        }



        return view
    }
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { imagen ->
        // La URI contiene la ubicación de la imagen seleccionada por el usuario
        imagen.
    }

}