package com.zy.proyecto_final.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.zy.proyecto_final.R
import com.zy.proyecto_final.util.MD5util
import com.zy.proyecto_final.viewmodel.UserViewModel

class UpdatePwdActivity : AppCompatActivity() {
    private lateinit var et_newPassword: EditText
    private lateinit var et_newPasswordConf: EditText
    private lateinit var et_oldPassword: EditText
    private lateinit var btn_update: Button
    private lateinit var toolbar: Toolbar
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pwd)
        et_newPassword = findViewById(R.id.newPassword)
        et_newPasswordConf = findViewById(R.id.newPasswordConf)
        et_oldPassword = findViewById(R.id.oldPassword)
        btn_update = findViewById(R.id.updatePassword)
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        viewModel.init(this)
        btn_update.setOnClickListener {
            val newPassword = et_newPassword.text.toString()
            val newPasswordConf = et_newPasswordConf.text.toString()
            if(newPassword != newPasswordConf){
                et_newPasswordConf.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }
            val oldPassword = et_oldPassword.text.toString()
            val oldPasswordMD5 = MD5util().getMD5(oldPassword)
            var user_id = intent.getLongExtra("user_id",0)
            val user = viewModel.getUserById(user_id)

            if(oldPasswordMD5 != user?.password){
                et_oldPassword.error = "La contraseña antigente no coincide"
            }
            else{
                val passwordMD5 = MD5util().getMD5(newPassword)
                user?.password = passwordMD5
                viewModel.updateUser(MD5util().getMD5(newPassword), user_id)
                setResult(1000)
                finish()
            }
        }
    }
}