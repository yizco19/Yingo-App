package com.zy.proyecto_final.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.zy.proyecto_final.R
import com.zy.proyecto_final.databinding.ActivityRegisterBinding
import com.zy.proyecto_final.pojo.User
import com.zy.proyecto_final.util.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {
    val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.init(this)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerButton = findViewById<Button>(R.id.register)

        registerButton.setOnClickListener {
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (password != confirmPassword) {
                binding.confirmPassword.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }
            val passwordMD5 = MD5util().getMD5(password)
            val user = User(username, email, passwordMD5)
            if (viewModel.add(user)) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
