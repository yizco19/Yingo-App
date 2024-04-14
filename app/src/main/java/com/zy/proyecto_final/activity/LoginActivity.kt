package com.zy.proyecto_final.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.ActivityLoginBinding
import com.zy.proyecto_final.retrofit.LoginData
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.util.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private var isLogin: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private val yingomodel: YingoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.init(this)
        yingomodel.init(this)

        // Inicializa los componentes de la vista
        val registerButton = findViewById<TextView>(R.id.registernow)
        val loginButton = findViewById<Button>(R.id.login)
        val checkRememberMe = findViewById<CheckBox>(R.id.rememberme)

        // Obtiene datos de SharedPreferences
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        // Comprueba si el usuario ya ha iniciado sesión
        isLogin = sharedPreferences.getBoolean("is_login", false)
        if (isLogin) {
            val currentTime = System.currentTimeMillis()
            val lastLoginTime = sharedPreferences.getLong("lastLoginTime", 0)
            if (currentTime - lastLoginTime > 1000 * 60 * 60 * 24) {
                clearSharedPreferences()
            } else {
                binding.emailorusername.setText(sharedPreferences.getString("emailorusername", ""))
                binding.password.setText(sharedPreferences.getString("password", ""))
                checkRememberMe.isChecked = true
            }
        }

        // Botón para registrar
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Botón para iniciar sesión
        loginButton.setOnClickListener {
            //login data


            val emailorusername = binding.emailorusername.text.toString()
            val password = binding.password.text.toString()
            var loginData = LoginData(emailorusername, password)
            val login = yingomodel.login(loginData)
            // Diferente de null
            //if (emailorusername.isNotEmpty() && password.isNotEmpty()) {
              //  val passwordMD5 = MD5util().getMD5(password)
                //if (viewModel.login(emailorusername, passwordMD5)) {
            if(login){
                Log.i("login status", login.toString())
                    with(sharedPreferences.edit()) {
                        putBoolean("is_login", true)
                        putString("emailorusername", emailorusername)
                        putString("password", password)
                        putLong("lastLoginTime", System.currentTimeMillis())
                        apply()
                    }

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userid", viewModel.userlogged.id)
                    startActivity(intent)
                    Toast.makeText(this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    // Mostrar alerta de usuario o contraseña incorrectos
                    AlertDialog.Builder(this)
                        .setTitle("Error de inicio de sesión")
                        .setMessage("El nombre de usuario o la contraseña son incorrectos. Por favor, inténtalo de nuevo.")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        // Check remember me
        checkRememberMe.setOnCheckedChangeListener { _, isChecked ->
            isLogin = isChecked
        }
        }

        // Check remember me



    private fun clearSharedPreferences() {
        with(sharedPreferences.edit()) {
            putBoolean("is_login", false)
            putString("emailorusername", "")
            putString("password", "")
            putLong("lastLoginTime", 0)
            apply()
        }
    }
}
