package com.zy.proyecto_final.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.zy.proyecto_final.R
import com.zy.proyecto_final.viewmodel.UserViewModel
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.databinding.ActivityRegisterBinding
import com.zy.proyecto_final.pojo.User

class RegisterActivity : AppCompatActivity() {
    private val  viewmodel: UserViewModel by activityViewModels<UserViewModel>()
    private lateinit var binding: ActivityRegisterBinding
    private var view: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val register = findViewById<Button>(R.id.register)

        register.setOnClickListener {
            val account = binding.account.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if(password != confirmPassword){
                binding.confirmPassword.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }

            val User = User(
                account
                ,password
            )
            if(viewmodel.add(User)){
                //volver al login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }
    }


}