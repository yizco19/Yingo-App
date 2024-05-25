package com.zy.proyecto_final.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.ActivityLoginBinding
import com.zy.proyecto_final.retrofit.LoginData
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private var isLogin: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences
    private val yingomodel: YingoViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

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
        val progressBar = findViewById<View>(R.id.progressBar)

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
            val emailorusername = binding.emailorusername.text.toString()
            val password = binding.password.text.toString()
            val loginData = LoginData(emailorusername, password)
            progressBar.visibility = View.VISIBLE // Mostrar ProgressBar

            // Aquí necesitas usar coroutines para manejar la operación de inicio de sesión de manera asíncrona
            lifecycleScope.launch {
                val login = withContext(Dispatchers.IO) {
                    try {
                        yingomodel.login(loginData)
                    } catch (e: Exception) {
                        Toast.makeText(this@LoginActivity, "El servidor no está disponible o el nombre de usuario/contraseña es incorrecto. Por favor, inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
                        false
                    }
                }

                progressBar.visibility = View.GONE // Ocultar ProgressBar

                if (login) {
                    // Inicio de sesión exitoso
                    Log.i("login status", login.toString())
                    // Guardar datos de inicio de sesión en SharedPreferences
                    with(sharedPreferences.edit()) {
                        putBoolean("is_login", true)
                        putString("emailorusername", emailorusername)
                        putString("password", password)
                        putLong("lastLoginTime", System.currentTimeMillis())
                        apply()
                    }

                    // Abrir la actividad principal
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    // Inicio de sesión fallido
                    // Mostrar alerta de usuario o contraseña incorrectos
                    AlertDialog.Builder(this@LoginActivity)
                        .setTitle("Error de inicio de sesión")
                        .setMessage("El servidor no está disponible o el nombre de usuario/contraseña es incorrecto. Por favor, inténtalo de nuevo.")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }

        // Check remember me
        checkRememberMe.setOnCheckedChangeListener { _, isChecked ->
            isLogin = isChecked
        }
    }

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
