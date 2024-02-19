package com.zy.proyecto_final.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.ActivityLoginBinding
import com.zy.proyecto_final.util.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    var is_login : Boolean = false;
    var mSharedPreferences : SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.viewModel.init(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // inicializa los componentes de la vista
        val registerButton = findViewById<TextView>(R.id.registernow)
        val loginButton = findViewById<Button>(R.id.login)
        val checkRememberMe = findViewById<CheckBox>(R.id.rememberme)


        //obteniendo datos de mSharedPreferences
        mSharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        //comprobando si el usuario ya ha iniciado sesión
        is_login = mSharedPreferences?.getBoolean("is_login", false) ?: false
        if (is_login) {
            binding.emailorusername.setText(this.mSharedPreferences?.getString("emailorusername", ""))
            binding.password.setText(this.mSharedPreferences?.getString("password", ""))
            checkRememberMe.isChecked = true
        }

        // Botón para registrar
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


        // Botón para iniciar sesión
        loginButton.setOnClickListener {
            val emailorusername = binding.emailorusername.text.toString()
            val password = binding.password.text.toString()
            // Diferente de null
            if (emailorusername.isNotEmpty() && password.isNotEmpty()) {
                val passwordMD5 = MD5util().getMD5(password)
                if (viewModel.login(emailorusername, passwordMD5)) {
                    var edit = mSharedPreferences?.edit()
                    edit?.putBoolean("is_login", true);
                    edit?.putString("emailorusername", emailorusername);
                    edit?.putString("password", password);
                    //
                    edit?.commit()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        //check remember me
        checkRememberMe.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (isChecked) {
                is_login = true
            }
        }
    }
}