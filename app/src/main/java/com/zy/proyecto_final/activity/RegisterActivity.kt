package com.zy.proyecto_final.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.ActivityRegisterBinding
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.retrofit.RegistrationData
import com.zy.proyecto_final.retrofit.YingoViewModel
import com.zy.proyecto_final.utils.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel
import okhttp3.internal.wait

class RegisterActivity : AppCompatActivity() {
    val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding
    private val yingomodel: YingoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.init(this)
        this.yingomodel.init(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)


// Encuentra el Toolbar por su ID
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        toolbar.setOnClickListener{
            finish()
        }


        val registerButton = findViewById<Button>(R.id.register)

        registerButton.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmpassword.text.toString()

            if (password != confirmPassword) {
                binding.confirmpassword.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }
            val passwordMD5 = MD5util().getMD5(password)
            val user = User(username, email, passwordMD5, null)
            if (viewModel.add(user)) {
                var registrationData = RegistrationData(username,email, password,"4983a0ab83ed86e0e7213c8783940193");

                val code = yingomodel.register(registrationData)
                if(code){
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
                }
                //espera 2 segundos antes de redirigir
                Thread.sleep(2000)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.back_login -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
