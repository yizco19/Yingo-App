import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.appcompat.widget.Toolbar
import com.zy.proyecto_final.R
import com.zy.proyecto_final.fragment.MineFragment
import com.zy.proyecto_final.fragment.SettingFragment
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.retrofit.entities.UpdatePwdData
import com.zy.proyecto_final.utils.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel

class UpdatePwdFragment : Fragment() {
    private lateinit var et_newPassword: EditText
    private lateinit var et_newPasswordConf: EditText
    private lateinit var et_oldPassword: EditText
    private lateinit var btn_update: Button
    private lateinit var toolbar: Toolbar
    private val viewModel: UserViewModel by activityViewModels()
    private val yingoviewModel: YingoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_update_pwd, container, false)
        et_newPassword = view.findViewById(R.id.newPassword)
        et_newPasswordConf = view.findViewById(R.id.newPasswordConf)
        et_oldPassword = view.findViewById(R.id.oldPassword)
        btn_update = view.findViewById(R.id.updatePassword)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, SettingFragment())?.commit()
        }

        yingoviewModel.init(requireContext())
        viewModel.init(requireContext())

        btn_update.setOnClickListener {
            val newPassword = et_newPassword.text.toString()
            val newPasswordConf = et_newPasswordConf.text.toString()
            //comprueba si la contraseña tiene longitud suficiente 5-16
            if (newPassword.length < 5 || newPassword.length > 16) {
                et_newPassword.error = "La contraseña debe contener entre 5 y 16 caracteres"
                return@setOnClickListener
            }
            if (newPassword != newPasswordConf) {
                et_newPasswordConf.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }
            val oldPassword = et_oldPassword.text.toString()
            val oldPasswordMD5 = MD5util().getMD5(oldPassword)
            val user_id = requireActivity().intent.getIntExtra("user_id", 0)
            val user = viewModel.getUserById(user_id)

            /*if(oldPasswordMD5 != user?.password){
                et_oldPassword.error = "La contraseña antigente no coincide"
            }
            else{*/
            val updatePwdData = UpdatePwdData(
                oldPassword,
                newPassword,
                newPasswordConf
            )
            val result = yingoviewModel.updatePwd(updatePwdData)
            if (result?.code == 0) {
                Toast.makeText(requireContext(), "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                //regresa a la pantalla anterior y muestra un mensaje
            } else {
                Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
            }
            /*
            val passwordMD5 = MD5util().getMD5(newPassword)
            user?.password = passwordMD5
            viewModel.updateUser(MD5util().getMD5(newPassword), user_id)*/
            requireActivity().setResult(1000)
            requireActivity().finish()
            //}
        }
        return view
    }
}
