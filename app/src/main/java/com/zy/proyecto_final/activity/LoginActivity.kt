package com.zy.proyecto_final.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.ActivityLoginBinding
import com.zy.proyecto_final.databinding.ActivityRegisterBinding
import com.zy.proyecto_final.util.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.viewModel.init(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        //boton para registrar
        val register = findViewById<Button>(R.id.register)
        register.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val login = findViewById<Button>(R.id.login)
        login.setOnClickListener{
            val emailorusername = binding.emailorusername.text.toString()
            val password = binding.password.text.toString()
            // diferente de null
            if(emailorusername.isNotEmpty() && password.isNotEmpty()){
                val passwordMD5 = MD5util().getMD5(password)
                if(viewModel.login(emailorusername, passwordMD5)){
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }

            }

        }

    }
}